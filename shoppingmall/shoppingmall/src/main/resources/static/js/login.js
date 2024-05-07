document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.getElementById("login-form");

    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();
        const id = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        const data = {
            id: id,
            password: password
        };

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/user/login", true);
        xhr.setRequestHeader("Content-Type", "application/json"); // 요청 헤더를 JSON으로 설정
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) { // 요청 완료
                if (xhr.status === 200) { // 요청 성공
                    // 여기에서 리다이렉션을 수행
                    window.location.href = "/";
                } else {
                    // 요청이 실패한 경우에 대한 처리
                    document.getElementById("label1").style.color = "red";
                    document.getElementById("label1").innerText = xhr.responseText;
                    document.getElementById("id").value = ""; // 아이디 입력란 비우기
                    document.getElementById("password").value = ""; //비밀번호 입력란 비우기
                }
            }
        };
        xhr.send(JSON.stringify(data));
    });

    document.getElementById("sign_up").addEventListener("click", function (){
        window.location.href = "/user/new";
    });
});
