jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) +
        $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) +
        $(window).scrollLeft()) + "px");
    return this;
};

$(document).keyup(function(e) {
    if(e.key == "Escape") {
        hide_modal('.modal');
    }
});

function show_modal(e, id) {
    $(id).css("display", "block");
    $('.selected_slot').removeClass('selected_slot');
    $(e).addClass('selected_slot');
    $(id).center();
    $(id + " input:first").focus();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function hide_modal(id) {
    $(id + " :input").val("");
    $(id).css('display', 'none');
    $('.selected_slot').removeClass('selected_slot');
    $('.cover').remove();
}

function select_doc(d) {
    $('#doctors > .list-item').css('background-color', '');
    $('#doctors > .selected').removeClass('selected');
    $(d).addClass('selected');

    var doc = $(d).attr('doc');
    $(".slot > .box[doc!='" + doc + "']").css("display", "none");
    $(".slot > .box[doc='" + doc + "']").css("display", "block");
    sessionStorage.setItem("currentDoc", doc);
}

function trim_fio(fio) {
    var fio_part = fio.split(" ");
    for(var i = 1; i < fio_part.length; i++) {
        fio_part[i] = fio_part[i].substr(0, 1) + ".";
    }
    return fio_part.join(" ");
}

function register() {
    var fio = $("#new_record input[name='fio']").val();
    var phone = $("#new_record input[name='phone']").val();
    var doc = Number($("#doctors .selected").attr('doc'));
    var date = $('.selected_slot').attr('date');
    var time = $('.selected_slot').attr('time');
    var size = Number($('.selected_slot').attr('size'));

    var json = { fio: fio, phone: phone, doc: doc, date: date, time: time, size: size};

    $.ajax({
        url: "/api/slot/add",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(slot) {
        $('.selected_slot').html("<div class='box pink' onclick='event.stopPropagation();' oncontextmenu='show_menu(this);return false;' sid='" + slot.id +
            "' doc='" + slot.doctor.id + "'><b>" + trim_fio(slot.client.fio) + "</b><br><span>" + slot.client.phone + "</span></div>");
        hide_modal('#new_record');
    });
}

var currentSlot;

function show_menu(obj) {
    $('.context-menu').css("display", "block");
    $('.context-menu').css("left", mouseX);
    $('.context-menu').css("top", mouseY);
    var slot = $(obj).attr('sid');
    currentSlot = slot;
}

var mouseX;
var mouseY;

function mouse_position(e) {
    mouseX = e.clientX;
    mouseY = e.clientY;
}

function remove_slot() {
    $.ajax({
        url: "/api/slot/" + currentSlot,
        method: "DELETE",
    }).done(function() {
        $(".slot > .box[sid='" + currentSlot + "']").remove();
    });
}

function show_reference(id) {
    $(".reference").css("display", "none");
    $(".reference#" + id).css("display", "block");
}

function company_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());
    if(Number($(e).attr('cid')) > 0) {
        $(id + " input[name='cid']").val($(e).attr('cid'));
        $(id + " input[name='name']").val($(e).text());
        $(id + " input[name='address']").val($(e).attr('address'));
        $(id + " input[name='director']").val($(e).attr('director'));
        $(id + " .footer .danger").css("display", "inline-flex");
    } else {
        $(id + " .footer .danger").css("display", "none");
    }
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function employee_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());

    $(id + " select[name='cid']").empty();
    $(id + " input[name='schedule']").removeAttr("checked");
    $("#company.reference .list-item[cid!='0']").each(function() {
        $(id + " select[name='cid']").append("<option value='" + $(this).attr('cid') + "'>" + $(this).text() + "</option>");
    });

    if(Number($(e).attr('eid')) > 0) {
        $(id + " input[name='eid']").val($(e).attr('eid'));
        $(id + " input[name='fio']").val($(e).text());
        $(id + " input[name='post']").val($(e).attr('post'));
        $(id + " select[name='cid']").val($(e).attr('cid')).change();
        if($(e).attr('scheduled') == 'true') {
            $(id + " input[name='schedule']").attr("checked", "checked");
        }
        $(id + " .footer .danger").css("display", "inline-flex");
    } else {
        $(id + " select[name='cid']:first-child").change();
        $(id + " .footer .danger").css("display", "none");
    }
    $("#employee_schedule").css("width", $(id + " input[name='fio']").css("width"));
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function remove_company() {
    var id = $("#modal_company input[name='cid']").val();
    $.ajax({
        url: "/api/company/" + id,
        method: "DELETE"
    }).done(function() {
        $("#company .list-item[cid='" + id + "']").remove();
        hide_modal('#modal_company');
    });
}

function remove_employee() {
    var id = $("#modal_employee input[name='eid']").val();
    $.ajax({
        url: "/api/employee/" + id,
        method: "DELETE"
    }).done(function() {
        $("#employee .list-item[eid='" + id + "']").remove();
        hide_modal('#modal_employee');
    });
}

function save_company() {
    var cid = Number($("#modal_company input[name='cid']").val());
    var name = $("#modal_company input[name='name']").val();
    var address = $("#modal_company input[name='address']").val();
    var director = $("#modal_company input[name='director']").val();

    var json = { id: cid, name: name, address: address, director: director};
    var method = (cid > 0 ? "PUT" : "POST");
    $.ajax({
        url: "/api/company/",
        method: method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(company) {
        if(method === "POST") {
            $('#company').append("<div class='list-item' cid='" + company.id + "' onclick='company_modal(this, \"#modal_company\")'></div>");
        }
        $("#company .list-item[cid='" + company.id + "']").text(company.name);
        $("#company .list-item[cid='" + company.id + "']").attr("address", company.address);
        $("#company .list-item[cid='" + company.id + "']").attr("director", company.director);
        hide_modal('#modal_company');
    });
}

function save_employee() {
    var eid = Number($("#modal_employee input[name='eid']").val());
    var cid = Number($("#modal_employee select[name='cid']").val());
    var fio = $("#modal_employee input[name='fio']").val();
    var post = $("#modal_employee input[name='post']").val();
    var scheduled = $("#modal_employee input[name='schedule']").prop('checked') !== false;

    var json = { id: eid, company: {id: cid}, fio: fio, post: post, scheduled: scheduled};

    var method = (eid > 0 ? "PUT" : "POST");
    $.ajax({
        url: "/api/employee/",
        method: method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(employee) {
        if(method === "POST") {
            $('#employee').append("<div class='list-item' eid='" + employee.id + "' onclick='employee_modal(this, \"#modal_employee\")'></div>");
        }
        $("#employee .list-item[eid='" + employee.id + "']").text(employee.fio);
        $("#employee .list-item[eid='" + employee.id + "']").attr("cid", employee.company.id);
        $("#employee .list-item[eid='" + employee.id + "']").attr("post", employee.post);
        $("#employee .list-item[eid='" + employee.id + "']").attr("scheduled", employee.scheduled);
        hide_modal('#modal_employee');
    });
}