$(document).ready(function() {
    function updatePriceAndTotal() {
        var totalAmount = 0;
        $('tbody tr').each(function() {
            const discountPercent = parseInt($(this).find('.discounted-price').attr('data-discount'), 10) || 0;
            var price = 0;
            if(discountPercent > 0) price = parseFloat($(this).find('.discounted-price').text().replace('원', ''));
            else price = parseFloat($(this).find('td:nth-child(3)').text().replace('원', ''));

            console.log($(this).find('.discounted-price').attr('data-discount'));
            totalAmount += price;
        });
        $('#totalAmount').text(totalAmount + '원');
    }

    // 수량 증가 버튼 클릭 이벤트 처리
    $('.increase-quantity').on('click', function() {
        updateQuantity(this, 1);
    });

    // 수량 감소 버튼 클릭 이벤트 처리
    $('.decrease-quantity').on('click', function() {
        updateQuantity(this, -1);
    });

    function updateQuantity(button, increment) {
        var $button = $(button);
        var $row = $button.closest('tr');
        var cartItemId = $button.attr('data-cart-item-id');
        var $quantitySpan = $row.find('.quantity');
        var currentQuantity = parseInt($quantitySpan.text());
        var newQuantity = currentQuantity + increment;

        // 수량이 1 이하로 떨어지지 않도록 합니다.
        if (newQuantity < 1) {
            alert('수량은 1보다 작을 수 없습니다.');
            return;
        }

        $.post('/cart/update', { cartItemId: cartItemId, quantity: newQuantity }, function(response) {
            if (response === 'success') {
                var $originPriceSpan = $row.find('.origin-price');
                var originPrice = parseFloat($originPriceSpan.text());
                var totalPrice = newQuantity * originPrice;
                const discountPercent = parseInt($row.find('.discounted-price').attr('data-discount'), 10) || 0;
                var discountPrice = totalPrice*(1-(discountPercent/100));
                // 수량과 총 가격 업데이트
                $quantitySpan.text(newQuantity);
                $row.find('.total-price').text(totalPrice + '원');
                //console.log('Updated total price:', totalPrice);

                if(discountPercent> 0){
                    $row.find('.discounted-price').text(discountPrice + '원')
                    console.log('Updated total price:', discountPrice);
                }

                updatePriceAndTotal();
            } else {
                alert('Failed to update cart item quantity.');
            }
        });
    }

    // 초기 로딩 시 가격 업데이트
    updatePriceAndTotal();

    //장바구니 삭제 코드
    const del = document.querySelectorAll(".del");
    let id = null;
    del.forEach(button => {
        button.addEventListener('click', function() {
            const row = button.closest('tr');
            id = row.getAttribute('data-cart-item-id');

            if (id) {
                const url = new URL('http://localhost:8080/cart/delete');
                url.searchParams.append('product_id', id);

                fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'Accept': 'application/json'
                    }
                })
                    .then(response => response.text())
                    .then(data => {
                        if(data === "success") updatePriceAndTotal();
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('There was an error with the post request.');
                    });
            }
        });
    });

    // 팝업과 관련된 요소들
    const popup = document.getElementById('coupon-popup');
    const closePopupButton = document.getElementById('close-popup');
    const couponList = document.getElementById('coupon-list');
    const priceElement = document.getElementById('price');

    // 각 쿠폰 적용 버튼에 이벤트 리스너 추가
    document.querySelectorAll('.coupon-button').forEach(button => {
        button.addEventListener('click', (event) => {
            const row = event.target.closest('tr'); // 버튼이 포함된 행 찾기
            let productPrice = parseFloat(row.querySelector('.origin-price').textContent);
            let quantity = parseInt(row.querySelector('.quantity').textContent, 10);
            let totalPrice = productPrice * quantity;
            const applyButton = popup.querySelector('.apply-coupon');
            const discountedPriceElement = popup.querySelector('.discounted-price-value');
            const changeButton = row.querySelector('.change-button');
            const cancelButton = row.querySelector('.cancel-button');
            // 팝업에 현재 가격 설정
            priceElement.textContent = `${totalPrice.toFixed(0)} 원`;

            // 쿠폰 리스트 동적 로딩
            fetch('/getCoupon')
                .then(response => response.json())
                .then(coupons => {
                    couponList.innerHTML = ''; // 이전 내용 지우기
                    coupons.forEach(coupon => {
                        console.log(coupon);
                        const label = document.createElement('label');
                        label.innerHTML = `
                            <input type="radio" name="coupon" value="${coupon.percent}" class="coupon-option">
                             ${coupon.percent}% 할인 쿠폰
                        `;
                        couponList.appendChild(label);
                    });
                });

            // 쿠폰 선택 시 가격 업데이트
            document.addEventListener('change', (e) => {
                if (e.target.classList.contains('coupon-option')) {
                    const discount = parseInt(e.target.value, 10);
                    const discountedPrice = totalPrice * (1 - discount / 100);
                    priceElement.textContent = `${discountedPrice.toFixed(0)} 원`;
                }
            });

            // 변경 버튼 클릭 시 처리 (예: 다시 쿠폰 적용)
            changeButton.addEventListener('click', () => {
                productPrice = parseFloat(row.querySelector('.origin-price').textContent);
                quantity = parseInt(row.querySelector('.quantity').textContent, 10);
                totalPrice = productPrice * quantity;
                const couponElement = document.querySelector('.coupon-option:checked');
                if (couponElement) {
                    const discount = parseInt(couponElement.value, 10);
                    const discountedPrice = totalPrice * (1 - discount / 100);
                    priceElement.textContent = `${discountedPrice.toFixed(0)} 원`;
                }
                popup.style.display = "grid";
            });

            cancelButton.addEventListener('click', () => {
                const discountedPriceCell = row.querySelector('.discounted-price-cell');
                const discountPriceSpan = discountedPriceCell.firstElementChild.firstElementChild;
                discountPriceSpan.setAttribute('data-discount', "0"); // 할인율 저장
                discountedPriceCell.style.display = 'none';

                const applyCell = row.querySelector('.coupon-apply-cell');
                applyCell.style.display = 'table-cell';

                updatePriceAndTotal();
            });
            // 쿠폰 적용 버튼 클릭 시 할인된 가격 계산
            applyButton.addEventListener('click', () => {
                const selectedCoupon = popup.querySelector('input[name="coupon"]:checked');
                if (selectedCoupon) {
                    const discountPercent = parseInt(selectedCoupon.value, 10);
                    const discountedPrice = totalPrice * (1 - discountPercent / 100);

                    // 할인된 가격을 테이블에 표시
                    const discountedPriceCell = row.querySelector('.discounted-price-cell');
                    const discountPriceSpan = discountedPriceCell.firstElementChild.firstElementChild;
                    discountPriceSpan.textContent = `${discountedPrice.toFixed(0)} 원`;
                    discountPriceSpan.setAttribute('data-discount', discountPercent.toString()); // 할인율 저장
                    discountedPriceCell.style.display = 'table-cell';
                    discountPriceSpan.nextElementSibling.style.display='grid';
                    const applyCell = row.querySelector('.coupon-apply-cell');
                    applyCell.style.display = 'none';
                    updatePriceAndTotal();

                    // 팝업 숨기기
                    popup.style.display = "none";
                }
            });

            // 팝업 표시

            popup.style.display="grid";
        });
    });

    // 팝업 숨기기
    closePopupButton.addEventListener('click', () => {
        popup.style.display = "none";
    });


    // 팝업 이동 가능하도록 하기
    makeDraggable(popup);

    // 팝업 이동 가능 함수
    function makeDraggable(element) {
        let offsetX, offsetY, isDragging = false;

        element.addEventListener('mousedown', (e) => {
            isDragging = true;
            offsetX = e.clientX - element.getBoundingClientRect().left;
            offsetY = e.clientY - element.getBoundingClientRect().top;
        });

        document.addEventListener('mousemove', (e) => {
            if (isDragging) {
                element.style.left = `${e.clientX - offsetX}px`;
                element.style.top = `${e.clientY - offsetY}px`;
            }
        });

        document.addEventListener('mouseup', () => {
            isDragging = false;
        });
    }

    document.getElementById("buy").addEventListener("click", function () {
        let name = document.getElementById("name").innerText;
        const row = document.getElementById("table").rows.length;
        const total = document.getElementById("totalAmount").innerText.replace("원", "");
        if(row > 2)
            name = name + " 외" + (String)(row-2) + "건";
        sessionStorage.setItem("product_name",name);
        sessionStorage.setItem("total",total);
        window.location.href="/pay";
    })

});