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
        var subCommentContainer = $("#comment-" + id);

        $.getJSON("/comment/" + id, function (data) {

            var items = [];

            $.each(data.data.reverse(), function (index, comment) {
                var mediaElementLeft = $("<div/>", {
                    "class": "media-left"
                }).append(
                    $("<img/>", {
                        "class": "media-object img-circle",
                        "src": comment.user.avatarUrl,
                    })
                );

                var userDiv = $("<div/>", {
                    "style": "display: inline-block"
                }).append(
                    $("<span/>", {
                        html: comment.user.name
                    }))

                var mediaBodyElement = $("<div/>", {
                    "class": "media-body",
                    "style": "position: relative"
                }).append(
                    userDiv
                ).append(
                    $("<div/>", {
                        "class": "text-desc comment-content",
                        "style": "display: inline-block",
                        html:new Date(comment.gmtCreate).toLocaleDateString()
                    })
                ).append(
                    $("<div/>", {
                        "class":"comment-content",
                        "style": "display: inline-block",
                        html: comment.content
                    })
                );


                var mediaElement = $("<div/>", {
                    "class": "section media"
                }).append(mediaElementLeft).append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",

                }).append(mediaElement);

                subCommentContainer.prepend(commentElement);

            });

            //
            // $( "<ul/>", {
            //     "class": "my-new-list",
            //     html: items.join( "" )
            // }).appendTo( "body" );
        });
    }

}


function selectTag(e) {
    var previous=$("#tag").val();
    var value= e.getAttribute("data-tag");
    //没添加
    if (previous.indexOf(value)==-1){
        if(previous){
            $("#tag").val(previous+','+value);
        }else {
            $("#tag").val(value);
        }
    }

}

function showSelectTag() {
  $("#select-tag").show();
  $("#select-tag-ul li:first-child").addClass('active');
  $("#select-tag-ul").next().children(':first').addClass('active');
}