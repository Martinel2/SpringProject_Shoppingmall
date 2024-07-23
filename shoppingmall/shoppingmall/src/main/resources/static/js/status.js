document.addEventListener("DOMContentLoaded", function() {
    var b = document.getElementById("birth").innerText;
    document.getElementById("birth").innerText
        = b.substring(0,4) + "년 " + b.substring(4,6) + "월 " + b.substring(6,8) + "일";

    var p = document.getElementById("phone").innerText;
    document.getElementById("phone").innerText
        = p.substring(0,3) + "-" + p.substring(3,7) + "-" + p.substring(7,11);


});