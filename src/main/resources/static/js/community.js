function post() {
    var questionId = $("#question-id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if (response.code =='200') {
                $("#comment-section").hide();
            }else{
                //登录异常
                if(response.code==2003){
                    var isAccepted = confirm(response.message);

                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=da4fbb10f58d1fc7e7c5&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }

                }
                alert(response.message)
            }
        },
        dataType: "json"
    });

}