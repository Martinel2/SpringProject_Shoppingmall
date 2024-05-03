document.addEventListener("DOMContentLoaded", function() {

    const signup=document.getElementById("signB");
    signup.addEventListener("click", function(event) {
        const id = document.getElementById("id").value;
        const password = document.getElementById("pw").value;
        const name = document.getElementById("name").value;
        const birth = document.getElementById("birth").value;
        const phone =
            document.getElementById("phone1").value +
            document.getElementById("phone2").value +
            document.getElementById("phone3").value ;
        const email = document.getElementById("email").value;
        const place = document.getElementById("place").value;

        const data = {
            id: id,
            password: password,
            name: name,
            birth: birth,
            phone: phone,
            email: email,
            place: place
        };

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/user/new", true);
        xhr.setRequestHeader("Content-Type", "application/json"); // 요청 헤더를 JSON으로 설정
        xhr.onreadystatechange = function() {
            if(xhr.status === 500){
                document.getElementById("label1").style.color = "red";
                document.getElementById("label1").innerText = "사용 불가능한 ID 입니다.";
                document.getElementById("id").value = ""; // 아이디 입력란 비우기
                document.getElementById("id").style.borderColor = "red";
                document.getElementById("id").focus();
            }
        };
        xhr.send(JSON.stringify(data));
    });

    document.getElementById("id").addEventListener("focusout", function() {

        var id = $("#id").val();

        if(id == '' || id.length == 0) {
            $("#label1").css("color", "red").text("공백은 ID로 사용할 수 없습니다.");
            return false;
        }
    });

});
