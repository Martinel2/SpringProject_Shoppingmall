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
                    ////파싱된 카테고리 데이터를 처리하기 위해 renderCategories() 함수에 전달합니다
                    console.log(categories);
                    renderCategories(categories);
                } else {
                    // 서버에서 오류가 발생했을 때 처리합니다.
                    console.error("Failed to fetch categories:", xhr.status);
                }
            }
        };
        xhr.open("GET", 'categories.json', true);
        xhr.send();
    }

    function renderCategories(categories) {
        // 카테고리 메뉴 요소 가져오기
        const categoryMenu = document.getElementById("category-menu");

        // 카테고리 데이터를 동적으로 HTML에 추가
        categories.forEach(category => {
            const categoryItem = document.createElement("div");
            categoryItem.textContent = category;
            categoryItem.classList.add("category-item");
            categoryItem.addEventListener("click", () => {
                // 사용자가 카테고리를 클릭했을 때 실행될 함수 호출
                redirectSearchResult(category);
            });
            categoryMenu.appendChild(categoryItem);
        });
    }

    function redirectSearchResult(category) {
        window.location.href = "searchResult.html";
    }

    // 페이지 로드 시 카테고리 데이터 가져오기
    fetchCategories();
});
