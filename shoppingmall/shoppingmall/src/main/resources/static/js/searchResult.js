document.addEventListener('DOMContentLoaded', function () {

    document.getElementById("status").addEventListener("click", function () {
        window.location.href = "/user/status";
    })

    document.getElementById("cart").addEventListener("click", function () {
        window.location.href = "/cart";
    })

    document.getElementById("search_button").addEventListener("click", function () {
        // 입력된 검색어 가져오기
        document.getElementById("search_form").addEventListener("submit", function (event) {
            event.preventDefault(); // 기본 폼 제출 동작 방지

            // 입력된 검색어 가져오기
            var searchQuery = document.getElementById("search_input").value.trim();

            // 검색어가 비어있는지 확인
            if (searchQuery === "") {
                alert("검색어를 입력하세요.");
                return;
            }

            // 검색 결과 페이지로 이동
            window.location.href = "/search?query=" + encodeURIComponent(searchQuery);
        });
    });

    // 모든 테이블 행에 대해 클릭 이벤트를 추가
    document.querySelectorAll('.category').forEach(row => {
        row.addEventListener('click', function () {
            const category = row.value;
            // 상품 상세 페이지로 이동
            redirectSearchCategory(category);
        });
    });

    function redirectSearchCategory(category) {
        // 주소창은 바꾸되 새로고침은 하지 않음
        const newUrl = "/search?category=" + encodeURIComponent(category);
        window.history.pushState({category}, '', newUrl);

        // 비동기로 데이터 요청
        fetch(newUrl)
            .then(res => res.text())
            .then(html => {
                // 서버에서 해당 카테고리 HTML 조각을 받아서 일부만 갱신
                document.querySelector('.category_list').innerHTML = extractProductHtml(html);
            });
    }

    function extractProductHtml(fullHtml) {
        // 임시 DOM에 넣고 필요한 부분만 가져오기
        const temp = document.createElement('div');
        temp.innerHTML = fullHtml;

        const productSection = temp.querySelector('.category_list');
        return productSection ? productSection.innerHTML : '';
    }


    document.getElementById('sort').addEventListener('change', function () {
        const query = document.getElementById('search_input').dataset.query;
        const category = document.getElementById('category_list').dataset.category;
        const sortSelected = document.getElementById('sort').value;
        const view = document.getElementById('sort_list').dataset.view;
        let url = query === 'default'? "/search?category="+encodeURIComponent(category) : "/search?query="+encodeURIComponent(query);
        url += "&sort=" + encodeURIComponent(sortSelected);
        if(view != 'default') url += "&view=" + encodeURIComponent(view);
        window.location.href = url;
    });

    document.getElementById("vertical_button").addEventListener("click", function () {
        const query = document.getElementById('search_input').dataset.query;
        const category = document.getElementById('category_list').dataset.category;
        const sort = document.getElementById('sort_list').dataset.sort;
        let url = query === 'default'? "/search?category="+encodeURIComponent(category) : "/search?query="+encodeURIComponent(query);
        if(sort != '0') url += "&sort=" + sort;
        url += "&view=" + encodeURIComponent(0);
        window.location.href = url;
    });
    document.getElementById("card_button").addEventListener("click", function () {
        const query = document.getElementById('search_input').dataset.query;
        const category = document.getElementById('category_list').dataset.category;
        const sort = document.getElementById('sort_list').dataset.sort;
        let url = query === 'default'? "/search?category="+encodeURIComponent(category) : "/search?query="+encodeURIComponent(query);
        if(sort != '0') url += "&sort=" + sort;
        url += "&view=" + encodeURIComponent(1);
        window.location.href = url;
    });

    // 모든 테이블 행에 대해 클릭 이벤트를 추가
    document.querySelectorAll('.clickable-row').forEach(row => {
        row.addEventListener('click', function (event) {
            const target = event.target;

            // 행 안의 버튼 클릭 시 이동 방지
            if (target.classList.contains('wish')) {
                return;
            }
            const productId = this.dataset.productId;
            // 상품 상세 페이지로 이동
            window.location.href = `/products/${productId}`;
        });
    });

    document.querySelectorAll('.wish').forEach(image => {
        image.addEventListener('click', function () {
            const productId = this.getAttribute('data-product-id');
            const isWishlist = this.src.includes('wishlist-wish');
            const countSpan = this.nextElementSibling;
            const url = new URL(isWishlist ? 'http://localhost:8080/delWishlist' : 'http://localhost:8080/addWishlist');
            url.searchParams.append('productId', productId);

            fetch(url, {
                method: 'POST',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
                .then(response => {
                    if (response.status === 401) {
                        // 401 상태 코드인 경우 로그인 창으로 이동할지 물어보는 prompt 표시
                        const shouldRedirect = confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?');
                        if (shouldRedirect) {
                            window.location.href = '/login'; // 로그인 페이지로 이동
                        }
                        throw new Error('Unauthorized'); // 에러를 throw하여 나머지 then 블록이 실행되지 않도록 함
                    } else {
                        return response.text();
                    }
                })
                .then(data => {
                    if(data === "success"){
                        // 성공적으로 처리된 경우 이미지 경로를 업데이트
                        if (isWishlist) {
                            this.src = '/images/icons/wishlist-nowish.png';
                            countSpan.textContent = "(" + (parseInt(countSpan.innerText.replace("(", "").replace(")", "")) - 1).toFixed(0) + ")";
                        } else {
                            this.src = '/images/icons/wishlist-wish.png';
                            countSpan.textContent = "(" + (parseInt(countSpan.innerText.replace("(", "").replace(")", "")) + 1).toFixed(0) + ")";
                        }
                    }

                })
                .catch(error => {
                    console.error('Error:', error);
                    if (error.message === 'Unauthorized') {
                        // 이미 처리된 경우 예외 처리
                    } else {
                        alert('네트워크 오류.');
                    }
                });
        });
    });
});