
let currentPage = 1;
const rowsPerPage = 10;
let allData = [];

function renderTable(data) {
    const tableBody = document.getElementById("visit-table-body");
    tableBody.innerHTML = "";
    const start = (currentPage - 1) * rowsPerPage;
    const end = start + rowsPerPage;
    const pageData = data.slice(start, end);

    for (let visit of pageData) {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${visit.timestamp}</td>
            <td>${visit.ip}</td>
            <td>${visit.city}</td>
            <td>${visit.country}</td>
            <td>${visit.isp}</td>
        `;
        tableBody.appendChild(row);
    }

    renderPagination(data.length);
}

function renderPagination(total) {
    const pagination = document.getElementById("pagination");
    pagination.innerHTML = "";
    const totalPages = Math.ceil(total / rowsPerPage);

    for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement("button");
        btn.innerText = i;
        if (i === currentPage) {
            btn.classList.add("disabled");
        } else {
            btn.onclick = () => {
                currentPage = i;
                renderTable(filteredData());
            };
        }
        pagination.appendChild(btn);
    }
}

function filteredData() {
    const queryInput = document.getElementById("search-input");
    const query = queryInput ? queryInput.value.toLowerCase() : "";
    return allData.filter(v => v.ip.toLowerCase().includes(query));
}

document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("search-input");
    if (searchInput) {
        searchInput.addEventListener("input", () => {
            currentPage = 1;
            renderTable(filteredData());
        });
    }

    fetch('/api/admin/visits')
        .then(res => res.json())
        .then(data => {
            allData = data.content.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
            renderTable(allData);
        });
});
