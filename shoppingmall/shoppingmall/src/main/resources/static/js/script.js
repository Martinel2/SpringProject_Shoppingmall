document.addEventListener("DOMContentLoaded", function() {
    // Dummy product data
    const products = [
        {
            name: "상품 1",
            price: 1000,
            image: "https://via.placeholder.com/150"
        },
        {
            name: "상품 2",
            price: 1500,
            image: "https://via.placeholder.com/150"
        },
        {
            name: "상품 3",
            price: 2000,
            image: "https://via.placeholder.com/150"
        }
    ];

    const productsSection = document.getElementById("products");
    const category_box = document.getElementById("category_box");
    const category_1depth = document.getElementById("category_1depth");
    const category_div = document.createElement("div");

    function submitSearch() {
        // 입력된 검색어 가져오기
        document.getElementById("search_form").addEventListener("submit", function(event) {
            event.preventDefault(); // 기본 폼 제출 동작 방지

            // 입력된 검색어 가져오기
            var searchQuery = document.getElementById("search_input").value.trim();

            // 검색어가 비어있는지 확인
            if (searchQuery === "") {
                alert("검색어를 입력하세요.");
                return;
            }

            // 검색 결과 페이지로 이동
            redirectSearchResult(searchQuery);
        });
    }

    document.getElementById("search_button").addEventListener("click", submitSearch());

    // Render products
    products.forEach((product, index) => {
        const productElement = document.createElement("section");
        productElement.classList.add("product");
        productElement.dataset.index = index;
        productElement.innerHTML = `
      <img src="${product.image}" alt="${product.name}">
      <h2>${product.name}</h2>
      <p>${product.price}원</p>
      <button class="add-to-cart">장바구니 담기</button>
    `;
        productsSection.appendChild(productElement);
    });

    function fetchCategories() {
        // AJAX를 사용하여 서버에서 카테고리 데이터를 가져오는 요청을 보냅니다.
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // 서버에서 성공적으로 데이터를 받았을 때 처리합니다.
                    const categories = JSON.parse(xhr.responseText);
                    ////파싱된 카테고리 데이터를 처리하기 위해 renderCategories() 함수에 전달합니다
                    renderCategories(categories);
                } else {
                    // 서버에서 오류가 발생했을 때 처리합니다.
                    console.error("Failed to fetch categories:", xhr.status);
                }
            }
        };
        xhr.open("GET", "/json/categories.json", true);
        xhr.send();
    }
    // 페이지 로드 시 카테고리 데이터 가져오기
    fetchCategories();

    function renderCategories(categories) {
        // 카테고리 메뉴 요소 가져오기
        category_div.classList.add("category_all_layer");

        // 카테고리 메뉴와 세부 카테고리 메뉴를 부모 요소에 추가
        category_div.appendChild(category_1depth);
        category_box.appendChild(category_div);
        //console.log(categories);
        // 카테고리 데이터를 동적으로 HTML에 추가
        categories.forEach(ajaxCategory => {
            const categoryItem = document.createElement("li");
            categoryItem.textContent = JSON.stringify(ajaxCategory.name).replace(/"/g, '');
            categoryItem.classList.add("category_item");

            const category_2depth = document.createElement("div");
            category_2depth.id = "category_2depth";
            categoryItem.appendChild(category_2depth);

            ajaxCategory.detail.forEach(detail => {
                const detailItem = document.createElement("li");
                detailItem.textContent = detail;
                detailItem.classList.add("detail_item");
                category_2depth.appendChild(detailItem);

                detailItem.addEventListener("click", () => {
                    // 사용자가 카테고리를 클릭했을 때 실행될 함수 호출
                    redirectSearchCategory(detail);
                    event.stopPropagation();
                });
            });

            // 마우스가 카테고리에 올라갔을 때 세부 카테고리 표시
            categoryItem.addEventListener("mouseenter", () => {
                categoryItem.id = "category_item_active";
                categoryItem.addEventListener("mouseenter", () => {
                    category_2depth.style.display = "inline-block";
                });
                categoryItem.addEventListener("mouseleave", () => {
                    category_2depth.style.display = "none";
                    categoryItem.id = "category_item";
                });
            });

            // 마우스가 카테고리에서 벗어났을 때 세부 카테고리 숨기기
            category_box.addEventListener("mouseleave", () => {
                category_2depth.style.display = "none";
            });

            categoryItem.addEventListener("click", () => {
                // 사용자가 카테고리를 클릭했을 때 실행될 함수 호출
                let text = '';
                const element = document.getElementById("category_item_active");
                // 자식 노드를 제외한 텍스트를 수집합니다.
                for (let i = 0; i < element.childNodes.length; i++) {
                    const node = element.childNodes[i];
                    if (node.nodeType === Node.TEXT_NODE) {
                        text += node.textContent;
                    }
                }
                redirectSearchCategory(text.trim());
            });
            category_1depth.appendChild(categoryItem);
        });
    }

    // 세부 카테고리를 표시하는 함수
   /* function showDetails(details, categoryItem) {
        // 부모 요소 위치에 따라 세부 카테고리 메뉴 위치 조정
        const parentRect = category_1depth.getBoundingClientRect();
        category_2depth.style.top = `${parentRect.top}px`;
        category_2depth.style.left = `${parentRect.right}px`;
        category_2depth.style.display = "inline-block";
    }
    */

    // 세부 카테고리를 숨기는 함수
    /*function hideDetails() {
        const detailMenu = document.getElementById("category_2depth");
        if (detailMenu) {
            detailMenu.style.display = "none";
        }
    }
    */

    function redirectSearchResult(search) {
        window.location.href = "/search?query=" + encodeURIComponent(search);
    }

    function redirectSearchCategory(category) {
        window.location.href = "/search?category=" + encodeURIComponent(category);
    }
});
