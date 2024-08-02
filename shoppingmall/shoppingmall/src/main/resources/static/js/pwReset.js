document.addEventListener("DOMContentLoaded", function() {

    let reCheckPw = false; //패스워드가 다시 작성한 패스워드와 일치하는지 체크하는 스위치
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
    document.getElementById("newPassword").addEventListener("focusout", function () {


        let pw = $("#newPassword").val().toString();

        //pw조건: 영문자,숫자,특수문자를 섞어 최소 8자리 이상
        checkPw = validatePassword(pw);

        if (!checkPw) {
            $("#label2").css("color", "red").text("패스워드 조건을 확인해주세요");
        } else {
            $("#label2").css("color", "green").text("사용할 수 있는 패스워드입니다.");
        }

        if (checkPw) {
            if (!(pw === check)) {
                $("#label3").css("color", "red").text("패스워드가 다릅니다.");
                reCheckPw = false;
            } else {
                if (checkPw) {
                    $("#label3").css("color", "green").text("패스워드가 올바릅니다.");
                    reCheckPw = true;
                }
            }
        } else {
            $("#label3").css("color", "red").text("패스워드 조건을 먼저 확인해주세요");
        }
    });

//재입력한 패스워드가 일치하는지 확인
    document.getElementById("check").addEventListener("focusout", function () {


        let pw = $("#newPassword").val().toString();
        let check = $("#check").val().toString();

        if (checkPw) {
            if (!(pw === check)) {
                $("#label3").css("color", "red").text("패스워드가 다릅니다.");
                reCheckPw = false;
            } else {
                if (checkPw) {
                    $("#label3").css("color", "green").text("패스워드가 올바릅니다.");
                    reCheckPw = true;
                }
            }
        } else {
            $("#label3").css("color", "red").text("패스워드 조건을 먼저 확인해주세요");
        }
    });

    document.getElementById("resetForm").addEventListener("submit",function () {

        if(!reCheckPw) {
            document.getElementById("label3").style.color = "red";
            alert("패스워드가 일치하는지 확인하세요");
            document.getElementById("check").value = ""; // 아이디 입력란 비우기
            document.getElementById("check").style.borderColor = "red";
            document.getElementById("check").focus();
            return;
        }
        else
            document.getElementById("label3").innerText ="패스워드가 올바릅니다";

        // Fetch API를 사용하여 비동기 요청 보내기
        // 유효성 검사가 통과하면 AJAX를 통해 폼 데이터 전송
        const formData = new FormData(this);

        fetch("/resetPW", {
            method: "POST",
            body: formData,
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        }).then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('네트워크 응답이 올바르지 않습니다.');
            }
        }).then(data => {
            alert(data);
            if (data === '비밀번호 재설정이 완료되었습니다.') {
                window.location.href = "/login";
            }
        }).catch(error => {
            console.error('오류:', error);
            alert('네트워크 오류.');
        });
    });
});