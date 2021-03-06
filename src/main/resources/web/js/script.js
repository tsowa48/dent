jQuery.fn.center = function () {
    this.css("position","absolute");
    this.css("top", Math.max(0, (($(window).height() - $(this).outerHeight()) / 2) +
        $(window).scrollTop()) + "px");
    this.css("left", Math.max(0, (($(window).width() - $(this).outerWidth()) / 2) +
        $(window).scrollLeft()) + "px");
    return this;
};

$(document).keyup(function(e) {
    if(e.key === "Escape") {
        if($('#patient_list').css('display') === 'none') {
            hide_modal('.modal');
        } else {
            $('#patient_list').css('display', 'none');
        }
    }
});

window.onerror = function (msg, url, line) {
    var json = { msg: msg, url: url, line: line };
    try {
        $.ajax({
            url: "/api/js/error",
            method: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(json)
        }).done(function (data) {
        });
    } catch(e) { }
};

function slot_modal(e) {
    var sid = $(e).attr("sid");
    $("#new_record").css("display", "block");
    $("#new_record").center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $("#new_record").before(cover);
    $("#new_record input").removeAttr("disabled");
    if(sid === undefined) {
        if (Number(sessionStorage.getItem("currentDoc")) === 0) {
            hide_modal('.modal');
            return;
        }
        $('.selected_slot').removeClass('selected_slot');
        $(e).addClass('selected_slot');
        //$("#new_record input:first").focus();
        $("#new_record .footer .register").css("display", "inline-flex");
        $("#new_record .footer .register_free").css("display", "inline-flex");
        $("#new_record .footer .unregister").css("display", "none");
    } else {
        currentSlot = sid;
        $("#new_record .footer .register").css("display", "none");
        $("#new_record .footer .register_free").css("display", "none");
        $("#new_record .footer .unregister").css("display", "inline-flex");
        $("#new_record input").prop('disabled', true);
        if($(e).attr("cid") === undefined) {
            //$("#new_record input").prop('disabled', true);
        } else {
            //$("#new_record input").prop('disabled', false);
            //$("#new_record input:first").focus();
            $("#new_record input[name='fio']").val($(e).find(".fio").text());
            $("#new_record input[name='phone']").val($(e).find(".phone").text());
            var note = $(e).find(".note").text();
            $("#new_record input[name='note']").val(note === undefined || note === null ? "" : note);
            //$("#new_record .footer .register").css("display", "inline-flex");
        }
    }
}

function hide_modal(id) {
    $(id + " :input").val("");
    $(id).css('display', 'none');
    $('.selected_slot').removeClass('selected_slot');
    $('#patient_list').css('display', 'none');
    $('.cover').remove();
}

function select_doc(d) {
    $('#doctors > .list-item').css('background-color', '');
    $('#doctors > .selected').removeClass('selected');
    $(d).addClass('selected');

    var doc = Number($(d).attr('doc'));
    if(doc === 0) {
        $(".slot > .box[doc!='" + doc + "']").css("display", "block");
    } else {
        $(".slot > .box[doc!='" + doc + "']").css("display", "none");
        $(".slot > .box[doc='" + doc + "']").css("display", "block");
    }
    sessionStorage.setItem("currentDoc", doc);
}

function trim_fio(fio) {
    var fio_part = fio.split(" ");
    for(var i = 1; i < fio_part.length; i++) {
        fio_part[i] = fio_part[i].substr(0, 1) + ".";
    }
    return fio_part.join(" ");
}

function register(enabled) {
    var doc = Number($("#doctors .selected").attr('doc'));
    var date = $('.selected_slot').attr('date');
    var time = $('.selected_slot').attr('time');
    var size = Number($('.selected_slot').attr('size'));

    var fio = $("#new_record input[name='fio']").val();

    var json;
    if(enabled && fio !== "") {
        var pid = Number($("#new_record input[name='pid']").val());
        var phone = $("#new_record input[name='phone']").val();
        var note = $("#new_record input[name='note']").val();
        note = (note === null ? "" : note);
        json = { fio: fio, phone: phone, doc: doc, date: date, time: time, size: size, note: note, pid: pid};
    } else {
        json = { doc: doc, date: date, time: time, size: size};
    }
    $.ajax({
        url: "/api/slot/add",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(slot) {
        if(slot.enabled) {
            $('.selected_slot').append("<div class='box pink' onclick='slot_modal(this);event.stopPropagation();' sid='" + slot.id +
                "' cid='" + slot.client.id + "' doc='" + slot.doctor.id + "'><span class='fio' style='display:none;'>" + slot.client.fio +
                "</span><b>" + trim_fio(slot.client.fio) + "</b><br><span class='phone'>" + slot.client.phone + "</span>" +
                "<br><span class='note'>" + (slot.note === undefined ? "": slot.note) + "</span></div>");
        } else {
            $('.selected_slot').append("<div class='box pink' onclick='slot_modal(this);event.stopPropagation();' sid='" + slot.id +
                "' doc='" + slot.doctor.id + "'></div>");
        }
    });
    hide_modal('.modal');
}

var currentSlot;

/*function show_menu(obj) {
    $('.context-menu').css("display", "block");
    var x = (mouseX + $('.context-menu').width()) > $(window).width() ? (mouseX - $('.context-menu').width()) : mouseX;
    var y = (mouseY + $('.context-menu').height()) > $(window).height() ? (mouseY - $('.context-menu').height()) : mouseY;
    $('.context-menu').css("left", x);
    $('.context-menu').css("top", y);
    var slot = $(obj).attr('sid');
    currentSlot = slot;
}*/

var mouseX;
var mouseY;

function mouse_position(e) {
    mouseX = e.clientX;
    mouseY = e.clientY;
}

function unregister() {
    $.ajax({
        url: "/api/slot/" + currentSlot,
        method: "DELETE",
    }).done(function() {
        $(".slot > .box[sid='" + currentSlot + "']").remove();
    });
    hide_modal('.modal');
}

function show_reference(t) {
    var id = $(t).attr("reference");
    $(".reference").css("display", "none");
    $(".reference#" + id).css("display", "block");
    $("#references_menu .list-item").removeClass('selected');
    $("#references_menu .list-item[reference='" + id + "']").addClass('selected');
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

function schedule_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());
    if(Number($(e).attr('sid')) > 0) {
        $(id + " input[name='sid']").val($(e).attr('sid'));
        $(id + " select[name='dow']").val($(e).attr('dow'));
        $(id + " input[name='start']").val($(e).attr('start'));
        $(id + " input[name='finish']").val($(e).attr('finish'));
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
    $(id + " input[name='faired']").removeAttr("checked");
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
        if($(e).attr('faired') == 'true') {
            $(id + " input[name='faired']").attr("checked", "checked");
        }
        $(id + " .footer .danger").css("display", "inline-flex");
    } else {
        $(id + " select[name='cid']:first-child").change();
        $(id + " .footer .danger").css("display", "none");
    }
    $("#employee_schedule").css("width", $(id + " input[name='fio']").css("width"));
    $("#employee_faired").css("width", $(id + " input[name='fio']").css("width"));
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function act_type_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());
    if(Number($(e).attr('aid')) > 0) {
        $(id + " input[name='aid']").val($(e).attr('aid'));
        $(id + " input[name='name']").val($(e).text());
        $(id + " .footer .danger").css("display", "inline-flex");
    } else {
        $(id + " .footer .danger").css("display", "none");
    }
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function service_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());
    $(id + " select[name='atid']").empty();
    $("#act_type.reference .list-item[aid!='0']").each(function() {
        $(id + " select[name='atid']").append("<option value='" + $(this).attr('aid') + "'>" + $(this).text() + "</option>");
    });
    if(Number($(e).attr('sid')) > 0) {
        $(id + " input[name='sid']").val($(e).attr('sid'));
        $(id + " select[name='atid']").val($(e).attr('atid')).change();
        $(id + " input[name='name']").val($(e).text());
        $(id + " input[name='price']").val($(e).attr('price'));
        $(id + " .footer .danger").css("display", "inline-flex");
    } else {
        $(id + " .footer .danger").css("display", "none");
    }
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function manipulation_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());

    $(id + " select[name='sid']").empty();
    $("#service.reference .list-item[sid!='0']").each(function() {
        var act_type = $("#act_type.reference .list-item[aid='" + $(this).attr('atid') + "']").text();
        $(id + " select[name='sid']").append("<option value='" + $(this).attr('sid') + "'>" + $(this).text() + " (" + act_type + ")</option>");
    });

    if(Number($(e).attr('mid')) > 0) {
        $(id + " input[name='mid']").val($(e).attr('mid'));
        $(id + " input[name='name']").val($(e).text());
        $(id + " select[name='sid']").val($(e).attr('sid')).change();
        $(id + " .footer .danger").css("display", "inline-flex");
    } else {
        $(id + " select[name='sid']:first-child").change();
        $(id + " .footer .danger").css("display", "none");
    }
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function document_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());
    if(Number($(e).attr('did')) > 0) {
        $(id + " input[name='did']").val($(e).attr('did'));
        $(id + " select[name='type']").val($(e).attr('type')).change();
        $(id + " input[name='name']").val($(e).text());
        //$(id + " input[name='template']").css("display", "none");
        $(id + " .footer .danger").css("display", "inline-flex");
    } else {
        $(id + " .footer .danger").css("display", "none");
    }
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function history_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .header #header_text").text($(e).text());

    if(Number($(e).attr('hid')) > 0) {
        $(id + " .footer .info").css('display', 'inline-flex');

        $(id + " input[name='uid']").val(Number($(e).attr('uid')));
        $(id + " input[name='params']").val("&history=" + $(e).attr('hid'));
        $(id + " input[name='hid']").val($(e).attr('hid'));
        $(id + " input[name='did']").val($(e).attr('did'));
        $.ajax({
            url: "/api/history/" + Number($(e).attr('hid')),
            method: "GET"
        }).done(function(history) {
            if(history.props !== undefined) {
                if (history.props.complaints !== undefined) {
                    $(id + " input[name='props.complaints']").val(history.props.complaints);
                }
                if (history.props.gepatit === true) {
                    $(id + " input[name='props.gepatit']").attr("checked", "checked");
                }
                if (history.props.tuber === true) {
                    $(id + " input[name='props.tuber']").attr("checked", "checked");
                }
                if (history.props.pedi === true) {
                    $(id + " input[name='props.pedi']").attr("checked", "checked");
                }
                $(id + " input[name='props.break']").val(history.props.break);
                $(id + " input[name='props.manipulation']").val(history.props.manipulation);
                $(id + " input[name='props.sick']").val(history.props.sick);
                $(id + " input[name='props.visit']").val(history.props.visit);
                $(id + " input[name='props.allergy']").val(history.props.allergy);
                $(id + " input[name='props.outer']").val(history.props.outer);

                var dent = history.props.dent;
                var ds = $('#dent_status');
                dent.forEach(function(d) {
                    ds.find("select[tr='" + d.tr + "'][td='" + d.td + "'][half='" + d.half + "']").val(d.value).change();
                });

                $(id + " input[name='props.bite']").val(history.props.bite);
                $(id + " input[name='props.mucous']").val(history.props.mucous);
                $(id + " input[name='props.lab']").val(history.props.lab);
                $(id + " input[name='props.diagnosis']").val(history.props.diagnosis);
            }
        });
    } else {
        $(id + " input[name='props.gepatit']").removeAttr("checked");
        $(id + " input[name='props.tuber']").removeAttr("checked");
        $(id + " input[name='props.pedi']").removeAttr("checked");

        $(id + " .footer .info").css('display', 'none');
    }
    $(id + " input:first").focus();
    $(id).center();
    var cover = $("<div class='cover' onclick=\"hide_modal('.modal');\"></div>");
    $(id).before(cover);
}

function patient_modal(e, id) {
    $(id).css("display", "block");
    $(id + " .footer .info").css("display", "none");
    if (Number($(e).attr('pid')) > 0) {
        $(id + " .header #header_text").text($(e).find(".fio").text());
        $(id + " input[name='pid']").val($(e).attr('pid'));
        $(id + " input[name='fio']").val($(e).find(".fio").text());
        $(id + " input[name='phone']").val($(e).find(".phone").text());
        $(id + " select[name='sex']").val($(e).find(".sex").text() === 'true' ? '1' : '0');
        var day = $(e).find(".birth").text().substr(0, 2);
        var month = $(e).find(".birth").text().substr(3, 2);
        var birth = $(e).find(".birth").text().substr(6, 4) + "-" + (month) + "-" + (day);
        $(id + " input[name='birth']").val(birth);
        $(id + " textarea[name='address']").val($(e).find(".address").text());
        $(id + " select[name='find_out']").val($(e).find(".find_out").text());
        $(id + " .footer .info").css("display", "inline-flex");
    } else if (Number($(e).attr('cid')) > 0) {
        $(id + " .header #header_text").text($(e).find(".fio").text());
        $(id + " input[name='cid']").val($(e).attr('cid'));
        $(id + " input[name='fio']").val($(e).find(".fio").text());
        $(id + " input[name='phone']").val($(e).find(".phone").text());
    } else {
        $(id + " .header #header_text").text($(e).text());
    }
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

function remove_schedule() {
    var id = $("#modal_schedule input[name='sid']").val();
    $.ajax({
        url: "/api/schedule/" + id,
        method: "DELETE"
    }).done(function() {
        $("#schedule .list-item[sid='" + id + "']").remove();
        hide_modal('#modal_schedule');
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

function remove_act_type() {
    var id = $("#modal_act_type input[name='aid']").val();
    $.ajax({
        url: "/api/act_type/" + id,
        method: "DELETE"
    }).done(function() {
        $("#act_type .list-item[aid='" + id + "']").remove();
        hide_modal('#modal_act_type');
    });
}

function remove_service() {
    var id = $("#modal_service input[name='sid']").val();
    $.ajax({
        url: "/api/service/" + id,
        method: "DELETE"
    }).done(function() {
        $("#service .list-item[sid='" + id + "']").remove();
        hide_modal('#modal_service');
    });
}

function remove_manipulation() {
    var id = $("#modal_manipulation input[name='mid']").val();
    $.ajax({
        url: "/api/manipulation/" + id,
        method: "DELETE"
    }).done(function() {
        $("#manipulation .list-item[mid='" + id + "']").remove();
        hide_modal('#modal_manipulation');
    });
}

function remove_document() {
    var id = $("#modal_document input[name='did']").val();
    $.ajax({
        url: "/api/document/" + id,
        method: "DELETE"
    }).done(function() {
        $("#document .list-item[did='" + id + "']").remove();
        hide_modal('#modal_document');
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
        hide_modal('.modal');
    });
}

function save_schedule() {
    var sid = Number($("#modal_schedule input[name='sid']").val());
    var dow = Number($("#modal_schedule select[name='dow']").val());
    var start = $("#modal_schedule input[name='start']").val().split(":");
    var finish = $("#modal_schedule input[name='finish']").val().split(":");

    var json = { id: sid, dow: dow, start: start[0] + ":" + start[1] + ":00", finish: finish[0] + ":" + finish[1] + ":00" };
    var method = (sid > 0 ? "PUT" : "POST");
    $.ajax({
        url: "/api/schedule/",
        method: method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(schedule) {
        if(method === "POST") {
            $('#schedule').append("<div class='list-item' sid='" + schedule.id + "' onclick='schedule_modal(this, \"#modal_schedule\")'></div>");
        }
        $("#schedule .list-item[sid='" + schedule.id + "']").text(schedule.dayOfWeek + " " + schedule.timeStart + " - " + schedule.timeFinish);
        $("#schedule .list-item[sid='" + schedule.id + "']").attr("dow", schedule.dow);
        $("#schedule .list-item[sid='" + schedule.id + "']").attr("start", schedule.start);
        $("#schedule .list-item[sid='" + schedule.id + "']").attr("finish", schedule.finish);
        hide_modal('.modal');
    });
}

function save_employee() {
    var eid = Number($("#modal_employee input[name='eid']").val());
    var cid = Number($("#modal_employee select[name='cid']").val());
    var fio = $("#modal_employee input[name='fio']").val();
    var post = $("#modal_employee input[name='post']").val();
    var scheduled = $("#modal_employee input[name='schedule']").prop('checked') !== false;
    var faired = $("#modal_employee input[name='faired']").prop('checked') !== false;

    var json = { id: eid, company: {id: cid}, fio: fio, post: post, scheduled: scheduled, faired: faired};

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
        $("#employee .list-item[eid='" + employee.id + "']").attr("faired", employee.faired);
        hide_modal('.modal');
    });
}

function save_act_type() {
    var aid = Number($("#modal_act_type input[name='aid']").val());
    var name = $("#modal_act_type input[name='name']").val();

    var json = { id: aid, name: name};
    var method = (aid > 0 ? "PUT" : "POST");
    $.ajax({
        url: "/api/act_type/",
        method: method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(act_type) {
        if(method === "POST") {
            $('#act_type').append("<div class='list-item' aid='" + act_type.id + "' onclick='act_type_modal(this, \"#modal_act_type\")'></div>");
        }
        $("#act_type .list-item[aid='" + act_type.id + "']").text(act_type.name);
        hide_modal('.modal');
    });
}

function save_service() {
    var sid = Number($("#modal_service input[name='sid']").val());
    var atid = Number($("#modal_service select[name='atid']").val());
    var name = $("#modal_service input[name='name']").val();
    var price = $("#modal_service input[name='price']").val();

    var json = { id: sid, name: name, price: price, actType: { id: atid } };
    var method = (sid > 0 ? "PUT" : "POST");
    $.ajax({
        url: "/api/service/",
        method: method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(service) {
        if(method === "POST") {
            $('#service').append("<div class='list-item' sid='" + service.id + "' onclick='service_modal(this, \"#modal_service\")'></div>");
        }
        $("#service .list-item[sid='" + service.id + "']").text(service.name);
        $("#service .list-item[sid='" + service.id + "']").attr("atid", service.actType.id);
        $("#service .list-item[sid='" + service.id + "']").attr("price", service.price);
        hide_modal('.modal');
    });
}

function save_manipulation() {
    var mid = Number($("#modal_manipulation input[name='mid']").val());
    var sid = Number($("#modal_manipulation select[name='sid']").val());
    var name = $("#modal_manipulation input[name='name']").val();

    var json = { id: mid, service: {id: sid}, name: name};

    var method = (mid > 0 ? "PUT" : "POST");
    $.ajax({
        url: "/api/manipulation/",
        method: method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(manipulation) {
        if(method === "POST") {
            $('#manipulation').append("<div class='list-item' mid='" + manipulation.id + "' onclick='manipulation_modal(this, \"#modal_manipulation\")'></div>");
        }
        $("#manipulation .list-item[mid='" + manipulation.id + "']").text(manipulation.name);
        $("#manipulation .list-item[mid='" + manipulation.id + "']").attr("sid", manipulation.sid);
        hide_modal('.modal');
    });
}

function save_document() {
    var did = Number($("#modal_document input[name='did']").val());
    var type = $("#modal_document select[name='type']").val();
    var name = $("#modal_document input[name='name']").val();
    var file = $("#modal_document input[name='template']").prop('files')[0];
    var reader = new FileReader();
    reader.readAsText(file);
    reader.onload = function(event) {
        var content = event.target.result;
        var json = { id: did, name: name, type: type, template: content};
        var method = (did > 0 ? "PUT" : "POST");
        $.ajax({
            url: "/api/document/",
            method: method,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(json)
        }).done(function(document) {
            if(method === "POST") {
                $('#document').append("<div class='list-item' did='" + document.id + "' onclick='document_modal(this, \"#modal_document\")'></div>");
            }
            $("#document .list-item[did='" + document.id + "']").text(document.name);
            $("#document .list-item[did='" + document.id + "']").attr("type", document.type);
            hide_modal('.modal');
        });
    };
}

function save_patient() {
    var cid = Number($("#modal_patient input[name='cid']").val());
    var pid = Number($("#modal_patient input[name='pid']").val());
    var fio = $("#modal_patient input[name='fio']").val();
    var birth = $("#modal_patient input[name='birth']").val();
    var phone = $("#modal_patient input[name='phone']").val();
    var address = $("#modal_patient textarea[name='address']").val();
    var findOut = $("#modal_patient select[name='find_out']").val();
    var isMale = Number($("#modal_patient select[name='sex']").val()) === 1;

    var json = { id: pid, fio: fio, address: address, birth: birth, phone: phone, male: isMale, findOut: { id: findOut } };

    var method = (pid > 0 ? "PUT" : "POST");
    $.ajax({
        url: "/api/patient/",
        method: method,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function(patient) {
        if(method === "POST") {
            $('#patients').append("<div class='list-item' pid='" + patient.id + "' onclick=\"patient_modal(this, '#modal_patient')\"></div>");
        }
        $("#patients .list-item[pid='" + patient.id + "']").html(
            "<span style='display:none;' class='fio'>" + patient.fio + "</span>" +
            "<span style='display:none;' class='phone'>" + patient.phone + "</span>" +
            "<span style='display:none;' class='address'>" + patient.address + "</span>" +
            "<span style='display:none;' class='sex'>" + patient.male + "</span>" +
            "<span style='display:none;' class='birth'>" + patient.birth + "</span>" +
            "<span style='display:none;' class='find_out'>" + patient.findOut.id + "</span>" +
            "<b>" + patient.fio + "</b> (" + patient.phone + ") " + patient.address);
        hide_modal('.modal');
        if(cid !== 0) {
            var json = {sql: "update client set pid = :pid where id = :cid", params: [{ "pid": patient.id}, {"cid": cid}]};
            $.ajax({
                url: "/api/sql/",
                method: "PUT",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(json)
            }).done(function(data) { });
            $("#unattached .list-item[cid='" + cid + "']").remove();
        }
    });
}