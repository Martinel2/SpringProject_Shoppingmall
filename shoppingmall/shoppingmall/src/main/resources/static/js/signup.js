document.addEventListener("DOMContentLoaded", function() {
    let checkPw = false; //패스워드가 조건에 맞는지 체크하는 스위치
    let reCheckPw = false; //패스워드가 다시 작성한 패스워드와 일치하는지 체크하는 스위치
    let checkId = false; // 공백 아이디 체크
    function checkName(){ //이름 체크
        var name = $("#name").val();

        if(name == '' || name.length == 0) {
            return false;
        }
        else{
            return true;
        }
    }
    function checkBirth(){ //생일 체크
        var birth = $("#birth").val();

        if(birth == '' || birth.length == 0) {
            return false;
        }
        else{ //30일만 있는 날, 윤년 처리는 아직 하지 않음
            // YYYYMMDD 형식인지 확인하는 정규 표현식
            const regex = /^\d{4}\d{2}\d{2}$/;
            if (!regex.test(birth)) {
                return false;
            }

            // 연도, 월, 일 추출
            const year = parseInt(birth.substring(0, 4), 10);
            const month = parseInt(birth.substring(4, 6), 10);
            const day = parseInt(birth.substring(6, 8), 10);

            // 월이 1~12 사이인지 확인
            if (month < 1 || month > 12) {
                return false;
            }

            // 일자가 해당 월에 존재하는지 확인
            const date = new Date(year, month - 1, day);
            return (
                date.getFullYear() === year &&
                date.getMonth() === month - 1 &&
                date.getDate() === day
            );
        }
    }
    function checkPhone(){ //전화번호 체크
        var phone2 = $("#phone2").val();
        var phone3 = $("#phone3").val();

        if(phone2 == '' || phone2.length == 0) {
            return false;
        }
        else{
            if(phone3 == '' || phone3.length == 0) {
                return false;
            }
            return true;
        }
    }

    function checkPlace(){ //주소 체크
        var place = $("#place").val();

        if(place == '' || place.length == 0) {
            return false;
        }
        else{
            return true;
        }
    }

    //회원가입 정보 받아서 서버로 보낸 후 결과에 따라 가입 성공 실패 코드
    const signup=document.getElementById("signB");
    signup.addEventListener("click", function(event) {
        if(!checkId){
            document.getElementById("label1").style.color = "red";
            document.getElementById("label1").innerText = "아이디를 작성해주세요.";
            document.getElementById("id").style.borderColor = "red";
            document.getElementById("id").focus();
            return;
        }
        else
            document.getElementById("label1").innerText ="";

        if(!reCheckPw) {
            document.getElementById("label3").style.color = "red";
            document.getElementById("label3").innerText = "패스워드가 일치하는지 확인하세요";
            document.getElementById("pw_check").value = ""; // 아이디 입력란 비우기
            document.getElementById("pw_check").style.borderColor = "red";
            document.getElementById("pw_check").focus();
            return;
        }
        else
            document.getElementById("label3").innerText ="패스워드가 올바릅니다";

        if(!checkName()){
            document.getElementById("label4").style.color = "red";
            document.getElementById("label4").innerText = "이름을 작성해주세요.";
            document.getElementById("name").style.borderColor = "red";
            document.getElementById("name").focus();
            return;
        }
        else
            document.getElementById("label4").innerText ="";

        if(!checkBirth()){
            document.getElementById("label5").style.color = "red";
            document.getElementById("label5").innerText = "생일을 확인해주세요.";
            document.getElementById("birth").style.borderColor = "red";
            document.getElementById("birth").focus();
            return;
        }
        else
            document.getElementById("label5").innerText ="";

        if(!checkPhone()){
            document.getElementById("label6").style.color = "red";
            document.getElementById("label6").innerText = "전화번호를 작성해주세요.";
            document.getElementById("phone1").style.borderColor = "red";
            document.getElementById("phone2").style.borderColor = "red";
            document.getElementById("phone3").style.borderColor = "red";
            document.getElementById("phone1").focus();
            return;
        }
        else
            document.getElementById("label6").innerText ="";

        if(!checkPlace()){
            document.getElementById("label8").style.color = "red";
            document.getElementById("label8").innerText = "주소를 작성해주세요.";
            document.getElementById("place").style.borderColor = "red";
            document.getElementById("place").focus();
            return;
        }
        else
            document.getElementById("label8").innerText ="";

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
        const enabled = "T";
        const role = "BASIC";
        const data = {
            id: id,
            password: password,
            name: name,
            birth: birth,
            phone: phone,
            email: email,
            place: place,
            enabled:enabled,
            role:role
        };
        $.ajax({
            url: '/user/new',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                // 성공적인 응답 처리
                if (response.message === "success") { // HTTP Created status
                    window.location.href = "/newUserComplete?id="+encodeURIComponent(id); // 성공 후 리다이렉트할 경로
                }
                },
            error: function(jqXHR, textStatus, errorThrown) {
                // 에러 처리
                alert("Error: " + jqXHR.responseText);
                console.log("Error: ", textStatus, errorThrown);
            }
        });
    });




        //아이디를 공백으로둘 시 오류
    document.getElementById("id").addEventListener("focusout", function() {

        var id = $("#id").val();

        if(id == '' || id.length == 0) {
            $("#label1").css("color", "red").text("공백은 ID로 사용할 수 없습니다.");
            checkId = false;
            return false;
        }
        else{
            checkId = true;
            $("#label1").text("");
        }
    });

    function validatePassword(password) {
        // 영문자, 숫자, 특수문자를 각각 하나 이상 포함하는지 확인
        var letter = /[a-zA-Z]/;
        var number = /[0-9]/;
        var special = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;

        // 비밀번호 길이가 8자리 이상인지 확인
        if (password.length < 8) {
            return false;
        }

        // 각각의 문자 유형이 최소 한 번씩 나타나는지 확인
        if (!letter.test(password) || !number.test(password) || !special.test(password)) {
            return false;
        }

        // 모든 규칙을 통과한 경우 true 반환
        return true;
    }

    //패스워드가 조건에 맞는지 확인
    document.getElementById("pw").addEventListener("focusout", function() {

        let pw = $("#pw").val().toString();
        let check = $("#pw_check").val().toString();
        //pw조건: 영문자,숫자,특수문자를 섞어 최소 8자리 이상
        checkPw = validatePassword(pw);

        if (!checkPw) {
            $("#label2").css("color", "red").text("패스워드 조건을 확인해주세요");
            reCheckPw = false;
        } else {
            $("#label2").css("color", "green").text("사용할 수 있는 패스워드입니다.");
        }

        if (checkPw && check.length > 0) {
            if (!(pw === check)) {
                $("#label3").css("color", "red").text("패스워드가 다릅니다.");
                reCheckPw = false;
            } else {
                if (checkPw) {
                    $("#label3").css("color", "green").text("패스워드가 올바릅니다.");
                    reCheckPw = true;
                }
            }
        }
    });

    //재입력한 패스워드가 일치하는지 확인
    document.getElementById("pw_check").addEventListener("focusout", function() {


        let pw = $("#pw").val().toString();
        let check = $("#pw_check").val().toString();

        if (checkPw && check.length > 0) {
            if (!(pw === check)) {
                $("#label3").css("color", "red").text("패스워드가 다릅니다.");
                reCheckPw = false;
            } else {
                if (checkPw) {
                    $("#label3").css("color", "green").text("패스워드가 올바릅니다.");
                    reCheckPw = true;
                }
            }
        }else {
            $("#label3").css("color", "red").text("패스워드를 다시 한번 입력해주세요");
            reCheckPw = false;
        }
    });

});
