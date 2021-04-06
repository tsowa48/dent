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
    var time = Number($('.selected_slot').attr('time'));
    var size = Number($('.selected_slot').attr('size'));

    var json = { fio: fio, phone: phone, doc: doc, date: date, time: time, size: size};

    $.ajax({
        url: "/slot/add",
        method: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json)
    }).done(function() {
        $('.selected_slot').html("<div class='box pink'><b>" + trim_fio(fio) + "</b><br><span>" + phone + "</span></div>");
        $('.selected_slot').removeAttr("onclick");
        hide_modal('#new_record');
    });
}