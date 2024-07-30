document.addEventListener("DOMContentLoaded", function() {
    /////////////////////////login page///////////////////////////////
    const login = document.getElementById("login");

    /*login.addEventListener("click", function(event) {
        loginCheck();
    });
    function loginCheck() {
        const id = document.getElementById("id").value;
        const password = document.getElementById("password").value;

        if(id == '' || id.length == 0) {
            document.getElementById("label1").style.color = "red";
            document.getElementById("label1").innerText = "아이디를 입력하세요.";
            document.getElementById("label1").focus();
            return false;
        }
        if(password == '' || password.length == 0) {
            document.getElementById("label2").style.color = "red";
            document.getElementById("label2").innerText = "비밀번호를 입력하세요.";
            document.getElementById("label2").focus();
            return false;
        }
        const data ={
            id: id,
            password: password
        };
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/login-process", true);
        xhr.setRequestHeader("Content-Type", "application/json"); // 요청 헤더를 JSON으로 설정
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) { // 요청 완료
                if (xhr.status === 200) { // 요청 성공
                    //console.log(xhr.responseText);
                    if(xhr.responseText === "success"){
                        // 여기에서 리다이렉션을 수행
                        return window.location.href = "/";
                    }
                    else{
                        document.getElementById("label1").style.color = "red";
                        document.getElementById("label1").innerText = xhr.responseText;
                        document.getElementById("id").value = ""; // 아이디 입력란 비우기
                        document.getElementById("password").value = ""; //비밀번호 입력란 비우기
                    }
                } else {
                    // 요청이 실패한 경우에 대한 처리
                    document.getElementById("label1").style.color = "red";
                    document.getElementById("label1").innerText = xhr.responseText;
                    document.getElementById("id").value = ""; // 아이디 입력란 비우기
                    document.getElementById("password").value = ""; //비밀번호 입력란 비우기

                    return "다시 시도해주세요";
                }
            }
        };
        xhr.send(JSON.stringify(data));
    }
*/
    /*document.getElementById("sign_up").addEventListener("click", function (){
        window.location.href = "/user/new";
    });*/
});
