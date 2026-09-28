// ============================================================
// MRF MANAGEMENT SYSTEM - SCRIPT.JS
// MANUAL ID VERSION
// ADD + EDIT + UPDATE + DELETE + NAVIGATION
// ============================================================

const API = "/api/mrf";

// ============================================================
// COMMON
// ============================================================

async function apiFetch(url, options = {}) {

    const response = await fetch(url, {
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...(options.headers || {})
        }
    });

    if (!response.ok) {

        let message = `Request failed (${response.status})`;

        try {
            const text = await response.text();
            if (text) message = text;
        } catch (e) {
            console.error(e);
        }

        throw new Error(message);
    }

    if (response.status === 204) {
        return null;
    }

    const text = await response.text();

    if (!text) {
        return null;
    }

    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
}


function getValue(id) {

    const element = document.getElementById(id);

    return element ? element.value : "";
}


function setValue(id, value) {

    const element = document.getElementById(id);

    if (element) {
        element.value = value ?? "";
    }
}


function setText(id, value) {

    const element = document.getElementById(id);

    if (element) {
        element.textContent = value;
    }
}


function clearForm(id) {

    const form = document.getElementById(id);

    if (form) {
        form.reset();
    }
}


function money(value) {

    const number = Number(value || 0);

    return "₹" + number.toLocaleString("en-IN", {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    });
}


function escapeHTML(value) {

    if (value === null || value === undefined) {
        return "";
    }

    return String(value)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}


function showError(error) {

    console.error(error);

    alert(
        error?.message ||
        "Something went wrong. Please check the backend."
    );
}


function confirmDelete(name) {

    return confirm(
        `Are you sure you want to delete ${name || "this record"}?`
    );
}


function scrollToForm(formId) {

    const form = document.getElementById(formId);

    if (form) {

        form.scrollIntoView({
            behavior: "smooth",
            block: "center"
        });
    }
}


// ============================================================
// PAGE NAVIGATION
// ============================================================

const pages = {

    dashboard: "dashboardPage",
    sorters: "sortersPage",
    inventory: "inventoryPage",
    production: "productionPage",
    sales: "salesPage",
    invoices: "invoicesPage",
    payments: "paymentsPage",
    purchases: "purchasesPage",
    bills: "billsPage",
    reports: "reportsPage"

};


const pageTitles = {

    dashboard: "MRF Dashboard",
    sorters: "Optical Sorters",
    inventory: "Inventory Management",
    production: "Production & Sorting",
    sales: "Sales Orders",
    invoices: "Customer Invoices",
    payments: "Customer Payments",
    purchases: "Purchase Orders",
    bills: "Vendor Bills",
    reports: "Financial Reports"

};


function openPage(pageName) {

    if (!pages[pageName]) {
        pageName = "dashboard";
    }


    // Hide all pages
    Object.values(pages).forEach(pageId => {

        const page = document.getElementById(pageId);

        if (page) {

            page.classList.remove("active-page");

            page.style.display = "none";
        }

    });


    // Show selected page
    const selectedPage =
        document.getElementById(pages[pageName]);


    if (selectedPage) {

        selectedPage.classList.add("active-page");

        selectedPage.style.display = "block";
    }


    // Sidebar active
    document.querySelectorAll(".nav-item").forEach(item => {

        item.classList.remove("active");

        if (item.dataset.page === pageName) {

            item.classList.add("active");
        }

    });


    // Page title
    setText(
        "pageTitle",
        pageTitles[pageName] || "MRF Dashboard"
    );


    // Load page
    if (pageName === "dashboard") {

        loadDashboard();

    } else if (pageName === "sorters") {

        loadSorters();

    } else if (pageName === "inventory") {

        loadInventory();

    } else if (pageName === "production") {

        loadProduction();

    } else if (pageName === "sales") {

        loadSalesOrders();

    } else if (pageName === "invoices") {

        loadInvoices();

    } else if (pageName === "payments") {

        loadPayments();

    } else if (pageName === "purchases") {

        loadPurchaseOrders();

    } else if (pageName === "bills") {

        loadVendorBills();
    }
}


function setupNavigation() {

    document.querySelectorAll("[data-page]").forEach(button => {

        button.addEventListener("click", function(event) {

            event.preventDefault();

            const page = this.dataset.page;

            if (page && pages[page]) {

                openPage(page);
            }

        });

    });
}


// ============================================================
// DASHBOARD
// ============================================================

async function loadDashboard() {

    try {

        const [
            inventory,
            profit
        ] = await Promise.all([

            apiFetch(`${API}/inventory`),

            apiFetch(`${API}/reports/profit-loss`)

        ]);


        const list =
            Array.isArray(inventory)
                ? inventory
                : [];


        // Inventory VALUE
        const inventoryValue =
            list.reduce(
                (sum, item) =>
                    sum +
                    Number(item.quantity || 0) *
                    Number(item.unitPrice || 0),
                0
            );


        const revenue =
            Number(profit?.totalRevenue || 0);


        const expenses =
            Number(profit?.totalExpenses || 0);


        const netProfit =
            Number(
                profit?.netProfitLoss ??
                profit?.netProfit ??
                0
            );


        setText(
            "inventoryValue",
            money(inventoryValue)
        );


        setText(
            "revenueValue",
            money(revenue)
        );


        setText(
            "expenseValue",
            money(expenses)
        );


        setText(
            "profitValue",
            money(netProfit)
        );


        const body =
            document.getElementById(
                "dashboardInventoryBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="4">
                        No inventory records found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.materialType)}
                    </td>

                    <td>
                        ${escapeHTML(item.quantity)}
                    </td>

                    <td>
                        ${escapeHTML(item.unit)}
                    </td>

                    <td>
                        ${money(item.unitPrice)}
                    </td>

                </tr>

            `;

        });

    } catch (error) {

        console.error("Dashboard error:", error);
    }
}


// ============================================================
// OPTICAL SORTERS
// ============================================================

function setupSorterForms() {

    const addForm =
        document.getElementById("sorterForm");


    const updateForm =
        document.getElementById("sorterUpdateForm");


    const deleteForm =
        document.getElementById("sorterDeleteForm");


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue("sorterId")
                        ),

                    sorterName:
                        getValue("sorterName"),

                    vendorName:
                        getValue("sorterVendor"),

                    sortingType:
                        getValue("sortingType"),

                    status:
                        getValue("sorterStatus")
                };


                try {

                    await apiFetch(
                        `${API}/sorters`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Optical Sorter added successfully!"
                    );


                    clearForm("sorterForm");

                    await loadSorters();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue("updateSorterId")
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Sorter ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    sorterName:
                        getValue(
                            "updateSorterName"
                        ),

                    vendorName:
                        getValue(
                            "updateSorterVendor"
                        ),

                    sortingType:
                        getValue(
                            "updateSortingType"
                        ),

                    status:
                        getValue(
                            "updateSorterStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/sorters/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Optical Sorter updated successfully!"
                    );


                    clearForm(
                        "sorterUpdateForm"
                    );


                    await loadSorters();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE FORM
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue("deleteSorterId")
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Sorter ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this optical sorter"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/sorters/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Optical Sorter deleted successfully!"
                    );


                    clearForm(
                        "sorterDeleteForm"
                    );


                    await loadSorters();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadSorters() {

    try {

        const data =
            await apiFetch(
                `${API}/sorters`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "sorterTableBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="6">
                        No optical sorters found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(item.sorterName)}
                    </td>

                    <td>
                        ${escapeHTML(item.vendorName)}
                    </td>

                    <td>
                        ${escapeHTML(item.sortingType)}
                    </td>

                    <td>
                        ${escapeHTML(item.status)}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editSorter(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deleteSorter(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editSorter(id) {

    try {

        const item =
            await apiFetch(
                `${API}/sorters/${id}`
            );


        if (!item) {

            alert(
                "Optical Sorter not found."
            );

            return;
        }


        setValue(
            "updateSorterId",
            item.id
        );


        setValue(
            "updateSorterName",
            item.sorterName
        );


        setValue(
            "updateSorterVendor",
            item.vendorName
        );


        setValue(
            "updateSortingType",
            item.sortingType
        );


        setValue(
            "updateSorterStatus",
            item.status
        );


        scrollToForm(
            "sorterUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deleteSorter(id) {

    if (
        !confirmDelete(
            "this optical sorter"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/sorters/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Optical Sorter deleted successfully!"
        );


        await loadSorters();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// INVENTORY
// ============================================================

function setupInventoryForms() {

    const addForm =
        document.getElementById("inventoryForm");


    const updateForm =
        document.getElementById(
            "inventoryUpdateForm"
        );


    const deleteForm =
        document.getElementById(
            "inventoryDeleteForm"
        );


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue("inventoryId")
                        ),

                    materialType:
                        getValue(
                            "inventoryMaterial"
                        ),

                    quantity:
                        Number(
                            getValue(
                                "inventoryQuantity"
                            )
                        ),

                    unit:
                        getValue(
                            "inventoryUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "inventoryPrice"
                            )
                        )
                };


                try {

                    await apiFetch(
                        `${API}/inventory`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Inventory added successfully!"
                    );


                    clearForm(
                        "inventoryForm"
                    );


                    await loadInventory();

                    await loadDashboard();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "updateInventoryId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Inventory ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    materialType:
                        getValue(
                            "updateInventoryMaterial"
                        ),

                    quantity:
                        Number(
                            getValue(
                                "updateInventoryQuantity"
                            )
                        ),

                    unit:
                        getValue(
                            "updateInventoryUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "updateInventoryPrice"
                            )
                        )
                };


                try {

                    await apiFetch(
                        `${API}/inventory/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Inventory updated successfully!"
                    );


                    clearForm(
                        "inventoryUpdateForm"
                    );


                    await loadInventory();

                    await loadDashboard();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE FORM
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "deleteInventoryId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Inventory ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this inventory record"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/inventory/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Inventory deleted successfully!"
                    );


                    clearForm(
                        "inventoryDeleteForm"
                    );


                    await loadInventory();

                    await loadDashboard();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadInventory() {

    try {

        const data =
            await apiFetch(
                `${API}/inventory`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "inventoryBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="6">
                        No inventory records found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(item.materialType)}
                    </td>

                    <td>
                        ${escapeHTML(item.quantity)}
                    </td>

                    <td>
                        ${escapeHTML(item.unit)}
                    </td>

                    <td>
                        ${money(item.unitPrice)}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editInventory(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deleteInventory(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editInventory(id) {

    try {

        const item =
            await apiFetch(
                `${API}/inventory/${id}`
            );


        if (!item) {

            alert(
                "Inventory record not found."
            );

            return;
        }


        setValue(
            "updateInventoryId",
            item.id
        );


        setValue(
            "updateInventoryMaterial",
            item.materialType
        );


        setValue(
            "updateInventoryQuantity",
            item.quantity
        );


        setValue(
            "updateInventoryUnit",
            item.unit
        );


        setValue(
            "updateInventoryPrice",
            item.unitPrice
        );


        scrollToForm(
            "inventoryUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deleteInventory(id) {

    if (
        !confirmDelete(
            "this inventory record"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/inventory/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Inventory deleted successfully!"
        );


        await loadInventory();

        await loadDashboard();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// PRODUCTION
// ============================================================

function setupProductionForms() {

    const addForm =
        document.getElementById(
            "productionForm"
        );


    const updateForm =
        document.getElementById(
            "productionUpdateForm"
        );


    const deleteForm =
        document.getElementById(
            "productionDeleteForm"
        );


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue(
                                "productionId"
                            )
                        ),

                    materialType:
                        getValue(
                            "productionMaterial"
                        ),

                    sortedWeight:
                        Number(
                            getValue(
                                "sortedWeight"
                            )
                        ),

                    unit:
                        getValue(
                            "productionUnit"
                        ),

                    sorterName:
                        getValue(
                            "productionSorter"
                        ),

                    productionDate:
                        getValue(
                            "productionDate"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/production`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Production record created successfully!"
                    );


                    clearForm(
                        "productionForm"
                    );


                    await loadProduction();

                    await loadInventory();

                    await loadDashboard();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "updateProductionId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Production ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    materialType:
                        getValue(
                            "updateProductionMaterial"
                        ),

                    sortedWeight:
                        Number(
                            getValue(
                                "updateSortedWeight"
                            )
                        ),

                    unit:
                        getValue(
                            "updateProductionUnit"
                        ),

                    sorterName:
                        getValue(
                            "updateProductionSorter"
                        ),

                    productionDate:
                        getValue(
                            "updateProductionDate"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/production/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Production record updated successfully!"
                    );


                    clearForm(
                        "productionUpdateForm"
                    );


                    await loadProduction();

                    await loadInventory();

                    await loadDashboard();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "deleteProductionId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Production ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this production record"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/production/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Production record deleted successfully!"
                    );


                    clearForm(
                        "productionDeleteForm"
                    );


                    await loadProduction();

                    await loadInventory();

                    await loadDashboard();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadProduction() {

    try {

        const data =
            await apiFetch(
                `${API}/production`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "productionBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="7">
                        No production records found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(item.materialType)}
                    </td>

                    <td>
                        ${escapeHTML(item.sortedWeight)}
                    </td>

                    <td>
                        ${escapeHTML(item.unit)}
                    </td>

                    <td>
                        ${escapeHTML(item.sorterName)}
                    </td>

                    <td>
                        ${escapeHTML(item.productionDate)}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editProduction(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deleteProduction(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editProduction(id) {

    try {

        const item =
            await apiFetch(
                `${API}/production/${id}`
            );


        if (!item) {

            alert(
                "Production record not found."
            );

            return;
        }


        setValue(
            "updateProductionId",
            item.id
        );


        setValue(
            "updateProductionMaterial",
            item.materialType
        );


        setValue(
            "updateSortedWeight",
            item.sortedWeight
        );


        setValue(
            "updateProductionUnit",
            item.unit
        );


        setValue(
            "updateProductionSorter",
            item.sorterName
        );


        setValue(
            "updateProductionDate",
            item.productionDate
        );


        scrollToForm(
            "productionUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deleteProduction(id) {

    if (
        !confirmDelete(
            "this production record"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/production/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Production record deleted successfully!"
        );


        await loadProduction();

        await loadInventory();

        await loadDashboard();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// SALES ORDERS
// ============================================================

function setupSalesForms() {

    const addForm =
        document.getElementById("salesForm");

    const updateForm =
        document.getElementById(
            "salesUpdateForm"
        );

    const deleteForm =
        document.getElementById(
            "salesDeleteForm"
        );


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue("salesId")
                        ),

                    brokerId:
                        Number(
                            getValue(
                                "salesBrokerId"
                            )
                        ),

                    brokerName:
                        getValue(
                            "salesBrokerName"
                        ),

                    productName:
                        getValue(
                            "salesProduct"
                        ),

                    materialType:
                        getValue(
                            "salesMaterial"
                        ),

                    quantity:
                        Number(
                            getValue(
                                "salesQuantity"
                            )
                        ),

                    unit:
                        getValue(
                            "salesUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "salesUnitPrice"
                            )
                        ),

                    totalAmount:
                        Number(
                            getValue(
                                "salesTotalAmount"
                            )
                        ),

                    orderDate:
                        getValue(
                            "salesDate"
                        ),

                    status:
                        getValue(
                            "salesStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/sales-orders`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Sales Order added successfully!"
                    );


                    clearForm("salesForm");

                    await loadSalesOrders();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "updateSalesId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Sales Order ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    brokerId:
                        Number(
                            getValue(
                                "updateSalesBrokerId"
                            )
                        ),

                    brokerName:
                        getValue(
                            "updateSalesBrokerName"
                        ),

                    productName:
                        getValue(
                            "updateSalesProduct"
                        ),

                    materialType:
                        getValue(
                            "updateSalesMaterial"
                        ),

                    quantity:
                        Number(
                            getValue(
                                "updateSalesQuantity"
                            )
                        ),

                    unit:
                        getValue(
                            "updateSalesUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "updateSalesUnitPrice"
                            )
                        ),

                    totalAmount:
                        Number(
                            getValue(
                                "updateSalesTotalAmount"
                            )
                        ),

                    orderDate:
                        getValue(
                            "updateSalesDate"
                        ),

                    status:
                        getValue(
                            "updateSalesStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/sales-orders/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Sales Order updated successfully!"
                    );


                    clearForm(
                        "salesUpdateForm"
                    );


                    await loadSalesOrders();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "deleteSalesId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Sales Order ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this sales order"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/sales-orders/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Sales Order deleted successfully!"
                    );


                    clearForm(
                        "salesDeleteForm"
                    );


                    await loadSalesOrders();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadSalesOrders() {

    try {

        const data =
            await apiFetch(
                `${API}/sales-orders`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "salesBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="8">
                        No sales orders found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(item.brokerName)}
                    </td>

                    <td>
                        ${escapeHTML(item.productName)}
                    </td>

                    <td>
                        ${escapeHTML(item.materialType)}
                    </td>

                    <td>
                        ${escapeHTML(item.quantity)}
                        ${escapeHTML(item.unit)}
                    </td>

                    <td>
                        ${money(item.totalAmount)}
                    </td>

                    <td>
                        ${escapeHTML(item.status)}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editSalesOrder(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deleteSalesOrder(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editSalesOrder(id) {

    try {

        const item =
            await apiFetch(
                `${API}/sales-orders/${id}`
            );


        if (!item) {

            alert(
                "Sales Order not found."
            );

            return;
        }


        setValue(
            "updateSalesId",
            item.id
        );

        setValue(
            "updateSalesBrokerId",
            item.brokerId
        );

        setValue(
            "updateSalesBrokerName",
            item.brokerName
        );

        setValue(
            "updateSalesProduct",
            item.productName
        );

        setValue(
            "updateSalesMaterial",
            item.materialType
        );

        setValue(
            "updateSalesQuantity",
            item.quantity
        );

        setValue(
            "updateSalesUnit",
            item.unit
        );

        setValue(
            "updateSalesUnitPrice",
            item.unitPrice
        );

        setValue(
            "updateSalesTotalAmount",
            item.totalAmount
        );

        setValue(
            "updateSalesDate",
            item.orderDate
        );

        setValue(
            "updateSalesStatus",
            item.status
        );


        scrollToForm(
            "salesUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deleteSalesOrder(id) {

    if (
        !confirmDelete(
            "this sales order"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/sales-orders/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Sales Order deleted successfully!"
        );


        await loadSalesOrders();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// CUSTOMER INVOICES
// ============================================================

function setupInvoiceForms() {

    const addForm =
        document.getElementById(
            "invoiceForm"
        );

    const updateForm =
        document.getElementById(
            "invoiceUpdateForm"
        );

    const deleteForm =
        document.getElementById(
            "invoiceDeleteForm"
        );


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue(
                                "invoiceId"
                            )
                        ),

                    salesOrderId:
                        Number(
                            getValue(
                                "invoiceSalesOrderId"
                            )
                        ),

                    brokerId:
                        Number(
                            getValue(
                                "invoiceBrokerId"
                            )
                        ),

                    brokerName:
                        getValue(
                            "invoiceBrokerName"
                        ),

                    productName:
                        getValue(
                            "invoiceProduct"
                        ),

                    materialType:
                        getValue(
                            "invoiceMaterial"
                        ),

                    deliveredWeight:
                        Number(
                            getValue(
                                "invoiceWeight"
                            )
                        ),

                    unit:
                        getValue(
                            "invoiceUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "invoicePrice"
                            )
                        ),

                    invoiceAmount:
                        Number(
                            getValue(
                                "invoiceAmount"
                            )
                        ),

                    invoiceDate:
                        getValue(
                            "invoiceDate"
                        ),

                    paymentStatus:
                        getValue(
                            "invoiceStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/customer-invoices`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Customer Invoice added successfully!"
                    );


                    clearForm(
                        "invoiceForm"
                    );


                    await loadInvoices();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "updateInvoiceId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Invoice ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    salesOrderId:
                        Number(
                            getValue(
                                "updateInvoiceSalesOrderId"
                            )
                        ),

                    brokerId:
                        Number(
                            getValue(
                                "updateInvoiceBrokerId"
                            )
                        ),

                    brokerName:
                        getValue(
                            "updateInvoiceBrokerName"
                        ),

                    productName:
                        getValue(
                            "updateInvoiceProduct"
                        ),

                    materialType:
                        getValue(
                            "updateInvoiceMaterial"
                        ),

                    deliveredWeight:
                        Number(
                            getValue(
                                "updateInvoiceWeight"
                            )
                        ),

                    unit:
                        getValue(
                            "updateInvoiceUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "updateInvoicePrice"
                            )
                        ),

                    invoiceAmount:
                        Number(
                            getValue(
                                "updateInvoiceAmount"
                            )
                        ),

                    invoiceDate:
                        getValue(
                            "updateInvoiceDate"
                        ),

                    paymentStatus:
                        getValue(
                            "updateInvoiceStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/customer-invoices/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Customer Invoice updated successfully!"
                    );


                    clearForm(
                        "invoiceUpdateForm"
                    );


                    await loadInvoices();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "deleteInvoiceId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Invoice ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this invoice"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/customer-invoices/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Customer Invoice deleted successfully!"
                    );


                    clearForm(
                        "invoiceDeleteForm"
                    );


                    await loadInvoices();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadInvoices() {

    try {

        const data =
            await apiFetch(
                `${API}/customer-invoices`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "invoiceBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="7">
                        No customer invoices found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(item.brokerName)}
                    </td>

                    <td>
                        ${escapeHTML(item.productName)}
                    </td>

                    <td>
                        ${escapeHTML(item.deliveredWeight)}
                        ${escapeHTML(item.unit)}
                    </td>

                    <td>
                        ${money(item.invoiceAmount)}
                    </td>

                    <td>
                        ${escapeHTML(item.paymentStatus)}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editInvoice(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deleteInvoice(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editInvoice(id) {

    try {

        const item =
            await apiFetch(
                `${API}/customer-invoices/${id}`
            );


        if (!item) {

            alert(
                "Customer Invoice not found."
            );

            return;
        }


        setValue(
            "updateInvoiceId",
            item.id
        );

        setValue(
            "updateInvoiceSalesOrderId",
            item.salesOrderId
        );

        setValue(
            "updateInvoiceBrokerId",
            item.brokerId
        );

        setValue(
            "updateInvoiceBrokerName",
            item.brokerName
        );

        setValue(
            "updateInvoiceProduct",
            item.productName
        );

        setValue(
            "updateInvoiceMaterial",
            item.materialType
        );

        setValue(
            "updateInvoiceWeight",
            item.deliveredWeight
        );

        setValue(
            "updateInvoiceUnit",
            item.unit
        );

        setValue(
            "updateInvoicePrice",
            item.unitPrice
        );

        setValue(
            "updateInvoiceAmount",
            item.invoiceAmount
        );

        setValue(
            "updateInvoiceDate",
            item.invoiceDate
        );

        setValue(
            "updateInvoiceStatus",
            item.paymentStatus
        );


        scrollToForm(
            "invoiceUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deleteInvoice(id) {

    if (
        !confirmDelete(
            "this invoice"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/customer-invoices/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Customer Invoice deleted successfully!"
        );


        await loadInvoices();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// CUSTOMER PAYMENTS
// ============================================================

function setupPaymentForms() {

    const addForm =
        document.getElementById(
            "paymentForm"
        );

    const updateForm =
        document.getElementById(
            "paymentUpdateForm"
        );

    const deleteForm =
        document.getElementById(
            "paymentDeleteForm"
        );


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue(
                                "paymentId"
                            )
                        ),

                    customerInvoiceId:
                        Number(
                            getValue(
                                "paymentInvoiceId"
                            )
                        ),

                    brokerId:
                        Number(
                            getValue(
                                "paymentBrokerId"
                            )
                        ),

                    brokerName:
                        getValue(
                            "paymentBrokerName"
                        ),

                    paymentAmount:
                        Number(
                            getValue(
                                "paymentAmount"
                            )
                        ),

                    paymentDate:
                        getValue(
                            "paymentDate"
                        ),

                    paymentMethod:
                        getValue(
                            "paymentMethod"
                        ),

                    paymentStatus:
                        getValue(
                            "paymentStatus"
                        ),

                    referenceNumber:
                        getValue(
                            "paymentReference"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/customer-payments`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Customer Payment added successfully!"
                    );


                    clearForm(
                        "paymentForm"
                    );


                    await loadPayments();

                    await loadInvoices();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "updatePaymentId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Payment ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    customerInvoiceId:
                        Number(
                            getValue(
                                "updatePaymentInvoiceId"
                            )
                        ),

                    brokerId:
                        Number(
                            getValue(
                                "updatePaymentBrokerId"
                            )
                        ),

                    brokerName:
                        getValue(
                            "updatePaymentBrokerName"
                        ),

                    paymentAmount:
                        Number(
                            getValue(
                                "updatePaymentAmount"
                            )
                        ),

                    paymentDate:
                        getValue(
                            "updatePaymentDate"
                        ),

                    paymentMethod:
                        getValue(
                            "updatePaymentMethod"
                        ),

                    paymentStatus:
                        getValue(
                            "updatePaymentStatus"
                        ),

                    referenceNumber:
                        getValue(
                            "updatePaymentReference"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/customer-payments/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Customer Payment updated successfully!"
                    );


                    clearForm(
                        "paymentUpdateForm"
                    );


                    await loadPayments();

                    await loadInvoices();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "deletePaymentId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Payment ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this customer payment"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/customer-payments/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Customer Payment deleted successfully!"
                    );


                    clearForm(
                        "paymentDeleteForm"
                    );


                    await loadPayments();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadPayments() {

    try {

        const data =
            await apiFetch(
                `${API}/customer-payments`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "paymentBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="7">
                        No customer payments found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(
                item.customerInvoiceId
            )}
                    </td>

                    <td>
                        ${escapeHTML(
                item.brokerName
            )}
                    </td>

                    <td>
                        ${money(
                item.paymentAmount
            )}
                    </td>

                    <td>
                        ${escapeHTML(
                item.paymentMethod
            )}
                    </td>

                    <td>
                        ${escapeHTML(
                item.paymentStatus
            )}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editPayment(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deletePayment(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editPayment(id) {

    try {

        const item =
            await apiFetch(
                `${API}/customer-payments/${id}`
            );


        if (!item) {

            alert(
                "Customer Payment not found."
            );

            return;
        }


        setValue(
            "updatePaymentId",
            item.id
        );

        setValue(
            "updatePaymentInvoiceId",
            item.customerInvoiceId
        );

        setValue(
            "updatePaymentBrokerId",
            item.brokerId
        );

        setValue(
            "updatePaymentBrokerName",
            item.brokerName
        );

        setValue(
            "updatePaymentAmount",
            item.paymentAmount
        );

        setValue(
            "updatePaymentDate",
            item.paymentDate
        );

        setValue(
            "updatePaymentMethod",
            item.paymentMethod
        );

        setValue(
            "updatePaymentStatus",
            item.paymentStatus
        );

        setValue(
            "updatePaymentReference",
            item.referenceNumber
        );


        scrollToForm(
            "paymentUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deletePayment(id) {

    if (
        !confirmDelete(
            "this customer payment"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/customer-payments/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Customer Payment deleted successfully!"
        );


        await loadPayments();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// PURCHASE ORDERS
// ============================================================

function setupPurchaseForms() {

    const addForm =
        document.getElementById(
            "purchaseForm"
        );

    const updateForm =
        document.getElementById(
            "purchaseUpdateForm"
        );

    const deleteForm =
        document.getElementById(
            "purchaseDeleteForm"
        );


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue(
                                "purchaseId"
                            )
                        ),

                    vendorName:
                        getValue(
                            "purchaseVendor"
                        ),

                    productName:
                        getValue(
                            "purchaseProduct"
                        ),

                    productType:
                        getValue(
                            "purchaseType"
                        ),

                    quantity:
                        Number(
                            getValue(
                                "purchaseQuantity"
                            )
                        ),

                    unit:
                        getValue(
                            "purchaseUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "purchasePrice"
                            )
                        ),

                    totalAmount:
                        Number(
                            getValue(
                                "purchaseTotalAmount"
                            )
                        ),

                    orderDate:
                        getValue(
                            "purchaseDate"
                        ),

                    status:
                        getValue(
                            "purchaseStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/purchase-orders`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Purchase Order added successfully!"
                    );


                    clearForm(
                        "purchaseForm"
                    );


                    await loadPurchaseOrders();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "updatePurchaseId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Purchase Order ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    vendorName:
                        getValue(
                            "updatePurchaseVendor"
                        ),

                    productName:
                        getValue(
                            "updatePurchaseProduct"
                        ),

                    productType:
                        getValue(
                            "updatePurchaseType"
                        ),

                    quantity:
                        Number(
                            getValue(
                                "updatePurchaseQuantity"
                            )
                        ),

                    unit:
                        getValue(
                            "updatePurchaseUnit"
                        ),

                    unitPrice:
                        Number(
                            getValue(
                                "updatePurchasePrice"
                            )
                        ),

                    totalAmount:
                        Number(
                            getValue(
                                "updatePurchaseTotalAmount"
                            )
                        ),

                    orderDate:
                        getValue(
                            "updatePurchaseDate"
                        ),

                    status:
                        getValue(
                            "updatePurchaseStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/purchase-orders/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Purchase Order updated successfully!"
                    );


                    clearForm(
                        "purchaseUpdateForm"
                    );


                    await loadPurchaseOrders();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "deletePurchaseId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Purchase Order ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this purchase order"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/purchase-orders/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Purchase Order deleted successfully!"
                    );


                    clearForm(
                        "purchaseDeleteForm"
                    );


                    await loadPurchaseOrders();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadPurchaseOrders() {

    try {

        const data =
            await apiFetch(
                `${API}/purchase-orders`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "purchaseBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="8">
                        No purchase orders found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(item.vendorName)}
                    </td>

                    <td>
                        ${escapeHTML(item.productName)}
                    </td>

                    <td>
                        ${escapeHTML(item.productType)}
                    </td>

                    <td>
                        ${escapeHTML(item.quantity)}
                        ${escapeHTML(item.unit)}
                    </td>

                    <td>
                        ${money(item.totalAmount)}
                    </td>

                    <td>
                        ${escapeHTML(item.status)}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editPurchaseOrder(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deletePurchaseOrder(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editPurchaseOrder(id) {

    try {

        const item =
            await apiFetch(
                `${API}/purchase-orders/${id}`
            );


        if (!item) {

            alert(
                "Purchase Order not found."
            );

            return;
        }


        setValue(
            "updatePurchaseId",
            item.id
        );

        setValue(
            "updatePurchaseVendor",
            item.vendorName
        );

        setValue(
            "updatePurchaseProduct",
            item.productName
        );

        setValue(
            "updatePurchaseType",
            item.productType
        );

        setValue(
            "updatePurchaseQuantity",
            item.quantity
        );

        setValue(
            "updatePurchaseUnit",
            item.unit
        );

        setValue(
            "updatePurchasePrice",
            item.unitPrice
        );

        setValue(
            "updatePurchaseTotalAmount",
            item.totalAmount
        );

        setValue(
            "updatePurchaseDate",
            item.orderDate
        );

        setValue(
            "updatePurchaseStatus",
            item.status
        );


        scrollToForm(
            "purchaseUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deletePurchaseOrder(id) {

    if (
        !confirmDelete(
            "this purchase order"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/purchase-orders/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Purchase Order deleted successfully!"
        );


        await loadPurchaseOrders();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// VENDOR BILLS
// ============================================================

function setupBillForms() {

    const addForm =
        document.getElementById(
            "billForm"
        );

    const updateForm =
        document.getElementById(
            "billUpdateForm"
        );

    const deleteForm =
        document.getElementById(
            "billDeleteForm"
        );


    // ADD
    if (addForm) {

        addForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const data = {

                    id:
                        Number(
                            getValue(
                                "billId"
                            )
                        ),

                    purchaseOrderId:
                        Number(
                            getValue(
                                "billPOId"
                            )
                        ),

                    vendorName:
                        getValue(
                            "billVendor"
                        ),

                    productName:
                        getValue(
                            "billProduct"
                        ),

                    billAmount:
                        Number(
                            getValue(
                                "billAmount"
                            )
                        ),

                    billDate:
                        getValue(
                            "billDate"
                        ),

                    paymentStatus:
                        getValue(
                            "billStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/vendor-bills`,
                        {
                            method: "POST",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Vendor Bill added successfully!"
                    );


                    clearForm(
                        "billForm"
                    );


                    await loadVendorBills();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // UPDATE
    if (updateForm) {

        updateForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "updateBillId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Vendor Bill ID."
                    );

                    return;
                }


                const data = {

                    id: id,

                    purchaseOrderId:
                        Number(
                            getValue(
                                "updateBillPOId"
                            )
                        ),

                    vendorName:
                        getValue(
                            "updateBillVendor"
                        ),

                    productName:
                        getValue(
                            "updateBillProduct"
                        ),

                    billAmount:
                        Number(
                            getValue(
                                "updateBillAmount"
                            )
                        ),

                    billDate:
                        getValue(
                            "updateBillDate"
                        ),

                    paymentStatus:
                        getValue(
                            "updateBillStatus"
                        )
                };


                try {

                    await apiFetch(
                        `${API}/vendor-bills/${id}`,
                        {
                            method: "PUT",
                            body: JSON.stringify(data)
                        }
                    );


                    alert(
                        "Vendor Bill updated successfully!"
                    );


                    clearForm(
                        "billUpdateForm"
                    );


                    await loadVendorBills();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }


    // DELETE
    if (deleteForm) {

        deleteForm.addEventListener(
            "submit",
            async function(event) {

                event.preventDefault();


                const id =
                    Number(
                        getValue(
                            "deleteBillId"
                        )
                    );


                if (!id) {

                    alert(
                        "Please enter a valid Vendor Bill ID."
                    );

                    return;
                }


                if (
                    !confirmDelete(
                        "this vendor bill"
                    )
                ) {
                    return;
                }


                try {

                    await apiFetch(
                        `${API}/vendor-bills/${id}`,
                        {
                            method: "DELETE"
                        }
                    );


                    alert(
                        "Vendor Bill deleted successfully!"
                    );


                    clearForm(
                        "billDeleteForm"
                    );


                    await loadVendorBills();

                } catch (error) {

                    showError(error);
                }

            }
        );
    }
}


async function loadVendorBills() {

    try {

        const data =
            await apiFetch(
                `${API}/vendor-bills`
            );


        const list =
            Array.isArray(data)
                ? data
                : [];


        const body =
            document.getElementById(
                "billBody"
            );


        if (!body) return;


        body.innerHTML = "";


        if (list.length === 0) {

            body.innerHTML = `
                <tr>
                    <td colspan="7">
                        No vendor bills found
                    </td>
                </tr>
            `;

            return;
        }


        list.forEach(item => {

            body.innerHTML += `

                <tr>

                    <td>
                        ${escapeHTML(item.id)}
                    </td>

                    <td>
                        ${escapeHTML(
                item.purchaseOrderId
            )}
                    </td>

                    <td>
                        ${escapeHTML(
                item.vendorName
            )}
                    </td>

                    <td>
                        ${escapeHTML(
                item.productName
            )}
                    </td>

                    <td>
                        ${money(
                item.billAmount
            )}
                    </td>

                    <td>
                        ${escapeHTML(
                item.paymentStatus
            )}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="action-btn edit-btn"
                            onclick="editVendorBill(${Number(item.id)})">

                            ✏️ Edit

                        </button>

                        <button
                            type="button"
                            class="action-btn delete-btn"
                            onclick="deleteVendorBill(${Number(item.id)})">

                            🗑️ Delete

                        </button>

                    </td>

                </tr>

            `;

        });

    } catch (error) {

        showError(error);
    }
}


async function editVendorBill(id) {

    try {

        const item =
            await apiFetch(
                `${API}/vendor-bills/${id}`
            );


        if (!item) {

            alert(
                "Vendor Bill not found."
            );

            return;
        }


        setValue(
            "updateBillId",
            item.id
        );

        setValue(
            "updateBillPOId",
            item.purchaseOrderId
        );

        setValue(
            "updateBillVendor",
            item.vendorName
        );

        setValue(
            "updateBillProduct",
            item.productName
        );

        setValue(
            "updateBillAmount",
            item.billAmount
        );

        setValue(
            "updateBillDate",
            item.billDate
        );

        setValue(
            "updateBillStatus",
            item.paymentStatus
        );


        scrollToForm(
            "billUpdateForm"
        );

    } catch (error) {

        showError(error);
    }
}


async function deleteVendorBill(id) {

    if (
        !confirmDelete(
            "this vendor bill"
        )
    ) {
        return;
    }


    try {

        await apiFetch(
            `${API}/vendor-bills/${id}`,
            {
                method: "DELETE"
            }
        );


        alert(
            "Vendor Bill deleted successfully!"
        );


        await loadVendorBills();

    } catch (error) {

        showError(error);
    }
}


// ============================================================
// FINANCIAL REPORTS
// ============================================================

async function showReport(type) {

    const output =
        document.getElementById(
            "reportOutput"
        );


    if (!output) return;


    output.innerHTML = `
        <div class="empty-state">
            <span>⏳</span>
            <p>Loading report...</p>
        </div>
    `;


    try {

        let data;


        // ----------------------------------------------------
        // PROFIT & LOSS
        // ----------------------------------------------------

        if (type === "profit") {

            data =
                await apiFetch(
                    `${API}/reports/profit-loss`
                );


            const revenue =
                Number(
                    data?.totalRevenue || 0
                );


            const expenses =
                Number(
                    data?.totalExpenses || 0
                );


            const netProfit =
                Number(
                    data?.netProfitLoss ??
                    data?.netProfit ??
                    0
                );


            output.innerHTML = `

                <div class="report-summary">

                    <div class="report-value-card">

                        <span>
                            Revenue
                        </span>

                        <strong>
                            ${money(revenue)}
                        </strong>

                    </div>


                    <div class="report-value-card">

                        <span>
                            Expenses
                        </span>

                        <strong>
                            ${money(expenses)}
                        </strong>

                    </div>


                    <div class="report-value-card">

                        <span>
                            Net Profit / Loss
                        </span>

                        <strong>
                            ${money(netProfit)}
                        </strong>

                    </div>

                </div>


                <div class="report-details">

                    <p>

                        Result:

                        <strong>
                            ${escapeHTML(
                data?.result ||
                "N/A"
            )}
                        </strong>

                    </p>

                </div>

            `;
        }


            // ----------------------------------------------------
            // BALANCE SHEET
        // ----------------------------------------------------

        else if (type === "balance") {

            data =
                await apiFetch(
                    `${API}/reports/balance-sheet`
                );


            output.innerHTML = `

                <div class="report-summary">

                    <div class="report-value-card">

                        <span>
                            Total Assets
                        </span>

                        <strong>
                            ${money(
                data?.totalAssets
            )}
                        </strong>

                    </div>


                    <div class="report-value-card">

                        <span>
                            Liabilities
                        </span>

                        <strong>
                            ${money(
                data?.totalLiabilities
            )}
                        </strong>

                    </div>


                    <div class="report-value-card">

                        <span>
                            Current Profit
                        </span>

                        <strong>
                            ${money(
                data?.currentProfit
            )}
                        </strong>

                    </div>


                    <div class="report-value-card">

                        <span>
                            Balance Status
                        </span>

                        <strong>

                            ${
                data?.balanced
                    ? "Balanced"
                    : "Not Balanced"
            }

                        </strong>

                    </div>

                </div>

            `;
        }


            // ----------------------------------------------------
            // BUDGET VARIANCE
        // ----------------------------------------------------

        else if (type === "budget") {

            data =
                await apiFetch(
                    `${API}/reports/budget-variance/1`
                );


            output.innerHTML = `

                <div class="table-container">

                    <table>

                        <thead>

                            <tr>

                                <th>
                                    Category
                                </th>

                                <th>
                                    Planned
                                </th>

                                <th>
                                    Actual
                                </th>

                                <th>
                                    Variance
                                </th>

                            </tr>

                        </thead>


                        <tbody>

                            <tr>

                                <td>
                                    Revenue
                                </td>

                                <td>
                                    ${money(
                data?.plannedRevenue
            )}
                                </td>

                                <td>
                                    ${money(
                data?.actualRevenue
            )}
                                </td>

                                <td>
                                    ${money(
                data?.revenueVariance
            )}
                                </td>

                            </tr>


                            <tr>

                                <td>
                                    Electricity Cost
                                </td>

                                <td>
                                    ${money(
                data?.plannedElectricityCost
            )}
                                </td>

                                <td>
                                    ${money(
                data?.actualElectricityCost
            )}
                                </td>

                                <td>
                                    ${money(
                data?.electricityVariance
            )}
                                </td>

                            </tr>


                            <tr>

                                <td>
                                    Maintenance Cost
                                </td>

                                <td>
                                    ${money(
                data?.plannedMaintenanceCost
            )}
                                </td>

                                <td>
                                    ${money(
                data?.actualMaintenanceCost
            )}
                                </td>

                                <td>
                                    ${money(
                data?.maintenanceVariance
            )}
                                </td>

                            </tr>


                            <tr>

                                <td>
                                    Total Expense
                                </td>

                                <td>
                                    ${money(
                data?.plannedTotalExpense
            )}
                                </td>

                                <td>
                                    ${money(
                data?.actualTotalExpense
            )}
                                </td>

                                <td>
                                    ${money(
                data?.expenseVariance
            )}
                                </td>

                            </tr>


                            <tr>

                                <td>
                                    Profit
                                </td>

                                <td>
                                    ${money(
                data?.plannedProfit
            )}
                                </td>

                                <td>
                                    ${money(
                data?.actualProfit
            )}
                                </td>

                                <td>
                                    ${money(
                data?.profitVariance
            )}
                                </td>

                            </tr>

                        </tbody>

                    </table>

                </div>

            `;
        }

    } catch (error) {

        console.error(
            "Report error:",
            error
        );


        output.innerHTML = `

            <div class="empty-state">

                <span>
                    ❌
                </span>

                <p>
                    Failed to load report.
                </p>

            </div>

        `;
    }
}


// ============================================================
// START APPLICATION
// ============================================================

document.addEventListener(
    "DOMContentLoaded",
    function() {

        // Navigation
        setupNavigation();


        // Form listeners
        setupSorterForms();

        setupInventoryForms();

        setupProductionForms();

        setupSalesForms();

        setupInvoiceForms();

        setupPaymentForms();

        setupPurchaseForms();

        setupBillForms();


        // Open Dashboard first
        openPage("dashboard");

    }
);