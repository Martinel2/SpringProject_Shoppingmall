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
    const userMenu = document.getElementById("user-menu");
    const categoryMenu = document.getElementById("category-menu");
    const category = document.getElementById("category");
    const parentMenu = document.createElement("div");
    let detailMenu = document.getElementById("detail-menu");

    // Dummy user data (Assuming not logged in initially)
    let isLoggedIn = false;

    // Function to update user menu
    function updateUserMenu() {
        userMenu.innerHTML = "";
        if (isLoggedIn) {
            const userIcon = document.createElement("div");
            userIcon.classList.add("user-icon");
            userIcon.textContent = "User";
            userIcon.addEventListener("click", toggleUserOptions);
            userMenu.appendChild(userIcon);
        } else {
            const loginButton = document.createElement("button");
            loginButton.classList.add("login-button");
            loginButton.textContent = "로그인";
            loginButton.addEventListener("click", redirectToLoginPage);
            userMenu.appendChild(loginButton);
        }
    }

    // Function to redirect to login page
    function redirectToLoginPage() {
        window.location.href = "login.html";
    }

    // Function to toggle user options dropdown
    function toggleUserOptions() {
        const userOptions = document.getElementById("user-options");
        userOptions.classList.toggle("show");
    }

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

    // Add event listener to document to close user options dropdown when clicked outside
    document.addEventListener("click", function(event) {
        if (!event.target.matches(".user-icon")) {
            const userOptions = document.getElementById("user-options");
            if (userOptions.classList.contains("show")) {
                userOptions.classList.remove("show");
            }
        }
    });

    updateUserMenu();

    function fetchCategories() {
        // AJAX를 사용하여 서버에서 카테고리 데이터를 가져오는 요청을 보냅니다.
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // 서버에서 성공적으로 데이터를 받았을 때 처리합니다.
                    const categories = JSON.parse(xhr.responseText);
                    //console.log(categories);
                    ////파싱된 카테고리 데이터를 처리하기 위해 renderCategories() 함수에 전달합니다
                    renderCategories(categories);
                } else {
                    // 서버에서 오류가 발생했을 때 처리합니다.
                    console.error("Failed to fetch categories:", xhr.status);
                }
            }
        };
        xhr.open("GET", "categories.json", true);
        xhr.send();
    }
    // 페이지 로드 시 카테고리 데이터 가져오기
    fetchCategories();

    function renderCategories(categories) {
        // 카테고리 메뉴 요소 가져오기
        parentMenu.classList.add("parent-menu");

        // 카테고리 메뉴와 세부 카테고리 메뉴를 부모 요소에 추가
        parentMenu.appendChild(category);
        categoryMenu.appendChild(parentMenu);

        // 카테고리 데이터를 동적으로 HTML에 추가
        categories.forEach(ajaxCategory => {
            const categoryItem = document.createElement("div");
            categoryItem.textContent = JSON.stringify(ajaxCategory.name).replace(/"/g, '');
            categoryItem.classList.add("ajaxCategory-item");

            // 마우스가 카테고리에 올라갔을 때 세부 카테고리 표시
            categoryItem.addEventListener("mouseenter", () => {
                showDetails(ajaxCategory.detail, categoryItem);
            });

            // 마우스가 카테고리에서 벗어났을 때 세부 카테고리 숨기기
            categoryMenu.addEventListener("mouseleave", () => {
                hideDetails();
            });


            categoryItem.addEventListener("click", () => {
                // 사용자가 카테고리를 클릭했을 때 실행될 함수 호출
                redirectSearchResult(ajaxCategory);
            });
            category.appendChild(categoryItem);
        });
    }

    // 세부 카테고리를 표시하는 함수
    // 세부 카테고리를 표시하는 함수
    function showDetails(details) {
        if (!detailMenu) {
            detailMenu = document.createElement("div");
            detailMenu.id = "detail-menu";
            category.appendChild(detailMenu);
        } else {
            detailMenu.innerHTML = ""; // 기존 세부 카테고리 지우기
        }

        details.forEach(detail => {
            const detailItem = document.createElement("div");
            detailItem.textContent = detail;
            detailItem.classList.add("detail-item");
            detailMenu.appendChild(detailItem);

            detailItem.addEventListener("click", () => {
                // 사용자가 카테고리를 클릭했을 때 실행될 함수 호출
                redirectSearchResult(details);
            });
        });

        // 부모 요소 위치에 따라 세부 카테고리 메뉴 위치 조정
        const parentRect = parentMenu.getBoundingClientRect();
        detailMenu.style.top = `${parentRect.top}px`;
        detailMenu.style.left = `${parentRect.right}px`;
        detailMenu.style.display = "block";
    }


    // 세부 카테고리를 숨기는 함수
    function hideDetails() {
        const detailMenu = document.getElementById("detail-menu");
        if (detailMenu) {
            detailMenu.style.display = "none";
        }
    }

    function redirectSearchResult(category) {
        window.location.href = "searchResult.html";
    }

});
