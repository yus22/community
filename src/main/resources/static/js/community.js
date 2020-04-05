/*
* 提交问题回复
*
* */
function post() {
    var questionId = $("#question-id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);

}

/*
* 提交回复
* */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

function comment2target(targetId, type, content) {
    if (content == "") {
        alert("评论内容不能为空");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == '200') {
                location.reload();
                $("#comment-section").hide();
            } else {
                //登录异常
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);

                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=da4fbb10f58d1fc7e7c5&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }

                }
                alert(response.message)
            }
        },
        dataType: "json"
    });
}

/*
* 展开二级评论
* */
function collapseComments(e) {
    var id = e.getAttribute("data-id");

    $("#comment-" + id).toggleClass("in");
    //有in 代表显示
    if ($("#comment-" + id).hasClass("in")) {
        $.getJSON("/comment/" + id, function (data) {
            console.log(data)
            var items = [];
            var commentBody=$("#comment-body-"+id);
            console.log(data.data)
            $.each(data.data, function (key, val) {
                items.push("<div class=\"section\">\n" +
                    "                                    <!--评论人头像-->\n" +
                    "                                    <div class=\"media-left\">\n" +
                    "                                        <a href=\"#\">\n" +
                    "                                            <img class=\"media-object img-circle\" th:src=\"${val.user.avatarUrl}\">\n" +
                    "                                        </a>\n" +
                    "                                    </div>\n" +
                    "                                    <div class=\"media-body\" style=\"position: relative\">\n" +
                    "                                        <div style=\"margin-top: 7px\"></div>\n" +
                    "                                        <div style=\"display: inline-block;\">\n" +
                    "                                            <span th:text=\"${val.user.name}\"></span>\n" +
                    "                                        </div>\n" +
                    "                                        <!--时间-->\n" +
                    "                                        <div style=\"display: inline-block\" class=\"text-desc\"\n" +
                    "                                             th:text=\"${#dates.format(val.gmtCreate,'yyyy-MM-dd')}\"></div>\n" +
                    "                                        <div style=\"display: inline-block;\" th:text=\"${val.content}\">\n" +
                    "                                        </div>\n" +
                    "                                    </div>\n" +
                    "\n" +
                    "                                    <hr>\n" +
                    "                                </div>");
            });

            commentBody.append(items);
            //
            // $( "<ul/>", {
            //     "class": "my-new-list",
            //     html: items.join( "" )
            // }).appendTo( "body" );
        });
    }

}