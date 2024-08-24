$(document).ready(function() {
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

/*    // 팝업과 관련된 요소들
    const popup = document.getElementById('coupon-popup');
    const closePopupButton = document.getElementById('close-popup');
    const couponList = document.getElementById('coupon-list');
    const priceElement = document.getElementById('price');
*/
    // 각 쿠폰 적용 버튼에 이벤트 리스너 추가
    document.querySelectorAll('.coupon-button').forEach(button => {
        button.addEventListener('click', (event) => {
            const row = event.target.closest('tr'); // 버튼이 포함된 행 찾기
            var cartItemId = row.getAttribute('data-cart-item-id');
            let quantity = parseInt(row.querySelector('.quantity').textContent, 10);
            let usedCouponIds = [];
            document.querySelectorAll('.discounted-price').forEach(function (discountedPriceSpan) {
                const couponId = discountedPriceSpan.getAttribute('data-couponId');
                if (couponId) {
                    usedCouponIds.push(couponId);
                }
            });

            const useCouponId = row.querySelector('.discounted-price').getAttribute('data-couponId') || 0;
            let productPrice = parseFloat(row.querySelector('.origin-price').textContent);
            let totalPrice = productPrice * quantity;

            const url = `/couponPopup?price=${encodeURIComponent(totalPrice)}&useCouponId=${encodeURIComponent(useCouponId)}&cartItemId=${encodeURIComponent(cartItemId)}&usedCouponIds=${encodeURIComponent(usedCouponIds.join(','))}`;
            const windowFeatures = 'width=300,height=600,menubar=no,toolbar=no,location=yes,status=no,resizable=no,scrollbars=yes';

            window.open(url, 'CouponPopup', windowFeatures);


            const changeButton = row.querySelector('.change-button');
            const cancelButton = row.querySelector('.cancel-button');

            // 변경 버튼 클릭 시 처리 (예: 다시 쿠폰 적용)
            changeButton.addEventListener('click', (event) => {
                var cartItemId = row.getAttribute('data-cart-item-id');
                let quantity = parseInt(row.querySelector('.quantity').textContent, 10);
                let usedCouponIds = [];
                document.querySelectorAll('.discounted-price').forEach(function (discountedPriceSpan) {
                    const couponId = discountedPriceSpan.getAttribute('data-couponId');
                    if (couponId) {
                        usedCouponIds.push(couponId);
                    }
                });

                const useCouponId = row.querySelector('.discounted-price').getAttribute('data-couponId') || 0;
                let productPrice = parseFloat(row.querySelector('.origin-price').textContent);
                let totalPrice = productPrice * quantity;

                const url = `/couponPopup?price=${encodeURIComponent(totalPrice)}&useCouponId=${encodeURIComponent(useCouponId)}&cartItemId=${encodeURIComponent(cartItemId)}&usedCouponIds=${encodeURIComponent(usedCouponIds.join(','))}`;
                const windowFeatures = 'width=300,height=600,menubar=no,toolbar=no,location=yes,status=no,resizable=no,scrollbars=yes';
                window.open(url, 'CouponPopup', windowFeatures);
            });

            cancelButton.addEventListener('click', () => {
                const discountedPriceCell = row.querySelector('.discounted-price-cell');
                const discountPriceSpan = discountedPriceCell.firstElementChild.firstElementChild;
                discountPriceSpan.setAttribute('data-discount', "0"); // 할인율 저장
                discountPriceSpan.setAttribute('data-couponId', "0"); // 할인율 저장
                discountedPriceCell.style.display = 'none';

                const applyCell = row.querySelector('.coupon-apply-cell');
                applyCell.style.display = 'table-cell';

                updatePriceAndTotal();
            });

        });
    });

    document.getElementById("buy").addEventListener("click", function () {
        let name = document.getElementById("name").innerText;
        const row = document.getElementById("table").rows.length;
        const total = document.getElementById("totalAmount").innerText.replace("원", "");
        const cartIds = [];
        const cartIdElements = document.querySelectorAll('.clickable-row');
        cartIdElements.forEach(elements => {
            let cartId = elements.dataset.cartItemId;
            cartIds.push(cartId);
        })
        const couponIds = [];
        const couponId = document.querySelectorAll('.discounted-price');
        couponId.forEach(elements => {
            let couponIdData = elements.dataset.couponId;
            couponIds.push(couponIdData);
        })
        const quantities = [];
        const quantity = document.querySelectorAll('.quantity');
        quantity.forEach(elements => {
            let q = elements.textContent;
            quantities.push(q);
        })
        const prices = [];
        $('tbody tr').each(function() {
            const discountPercent = parseInt($(this).find('.discounted-price').attr('data-discount'), 10) || 0;
            let price;
            if(discountPercent > 0) price = $(this).find('.discounted-price').text().replace('원', '').replace(' ', '');
            else price = $(this).find('td:nth-child(3)').text().replace('원', '').replace(' ', '');
            //console.log(price);
            prices.push(price);
        });
        if(row > 2)
            name = name + " 외" + (String)(row-2) + "건";
        sessionStorage.setItem("product_name",name);
        sessionStorage.setItem("total",total);
        // 배열을 JSON 문자열로 변환하여 sessionStorage에 저장
        sessionStorage.setItem('cartIds', JSON.stringify(cartIds));
        sessionStorage.setItem('couponId', JSON.stringify(couponIds));
        sessionStorage.setItem('quantity', JSON.stringify(quantities));
        sessionStorage.setItem('price', JSON.stringify(prices));

        window.location.href="/pay";
    })

});

function updatePriceAfterCoupon(cartItemId, couponId,finalPrice, percent) {
    // 할인된 가격을 테이블에 표시
    // cartItemId를 사용해 tr 요소 찾기
    const row = document.querySelector(`tr[data-cart-item-id='${cartItemId}']`);
    const discountedPriceCell = row.querySelector(`.discounted-price-cell`);
    const discountPriceSpan = discountedPriceCell.firstElementChild.firstElementChild;
    discountPriceSpan.textContent = `${finalPrice.toFixed(0)} 원`;
    discountPriceSpan.setAttribute('data-discount', percent.toString()); // 할인율 저장
    discountPriceSpan.setAttribute('data-couponId', couponId.toString()); // 쿠폰Id 저장
    discountedPriceCell.style.display = 'table-cell';
    discountPriceSpan.nextElementSibling.style.display='grid';
    const applyCell = row.querySelector('.coupon-apply-cell');
    applyCell.style.display = 'none';

    // 총 가격도 업데이트가 필요할 수 있음
    updatePriceAndTotal();
}

function updatePriceAndTotal() {
    var totalAmount = 0;
    $('tbody tr').each(function() {
        const discountPercent = parseInt($(this).find('.discounted-price').attr('data-discount'), 10) || 0;
        var price = 0;
        if(discountPercent > 0) price = parseInt($(this).find('.discounted-price').text().replace('원', ''));
        else price = parseInt($(this).find('td:nth-child(3)').text().replace('원', ''));

        //console.log($(this).find('.discounted-price').attr('data-discount'));
        totalAmount += price;
    });
    $('#totalAmount').text(totalAmount + '원');
}