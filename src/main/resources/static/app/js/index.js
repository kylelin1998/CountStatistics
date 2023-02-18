
function refCode() {
    var url = "/api/code/getImage?" + new Date();
    $("#code-image").attr("src", url)
}

$("#code").keydown(function (e) {
    if (e.keyCode === 13) {
        generate();
    }
});

function generate() {

    var code = $("#code").val();
    var securityCode = $("#securityCode").val();
    if (code === "") {
        UIkit.notification('请输入验证码');
        return;
    }
    if (securityCode === "") {
        UIkit.notification('请输入安全码');
        return;
    }

    var type = $("input[name='tjtype']:checked").val();

    if (type == 1) {
        // 计数
        $.ajax({
            url: '/api/use/generate',
            type: 'post',
            data: JSON.stringify({
                "code": code,
                "securityCode": securityCode
            }),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            success: function (res) {
                $("#generate-button").prop("disabled", false);
                if (res.code === 200) {
                    $(".use-total").text("今日已生成资源数量为: " + res.data.useTotal);
                    $(".uri").text("资源标识符为: " + res.data.uri + "  请保管好哟！");
                    $(".uri-modal").html(res.data.uri);
                    UIkit.modal($("#use-modal")).show();
                    refCode();
                    $("#code").val("");
                } else {
                    UIkit.notification(res.message);
                }
            },
            error: function(arg1) {
                $("#generate-button").prop("disabled", false);
                UIkit.notification('生成发生未知错误, 请联系站长处理！');
            }
        });
    } else if (type == 2) {
        // 在线统计
        $.ajax({
            url: '/api/online/generate',
            type: 'post',
            data: JSON.stringify({
                "code": code,
                "securityCode": securityCode
            }),
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            success: function (res) {
                $("#generate-button").prop("disabled", false);
                if (res.code === 200) {
                    $(".use-total").text("今日已生成资源数量为: " + res.data.useTotal);
                    $(".uri").text("资源标识符为: " + res.data.uri + "  请保管好哟！");
                    $(".uri-modal").html(res.data.uri);
                    UIkit.modal($("#online-modal")).show();
                    refCode();
                    $("#code").val("");
                } else {
                    UIkit.notification(res.message);
                }
            },
            error: function(arg1) {
                $("#generate-button").prop("disabled", false);
                UIkit.notification('生成发生未知错误, 请联系站长处理！');
            }
        });
    }
}