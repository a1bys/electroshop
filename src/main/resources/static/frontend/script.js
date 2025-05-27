    function toggleDropdown()
    {
        const element = document.getElementById("dropdown-menu").parentElement;
        element.classList.toggle("open");
    }
    function goBack()
    {
        window.history.back();
    }


    function parseCSV(text)
    {
        return text.trim().split('\n').map(line => line.split(','));
    }

    const csvInput = document.getElementById('csvFileInput');
    const tableBody = document.getElementById('storesTable').querySelector('tbody');

    csvInput.addEventListener('change', function()
    {
        const file = csvInput.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = function(e)
        {
            const rows = parseCSV(e.target.result);
            tableBody.innerHTML = '';
            rows.forEach(row =>
            {
                // Ожидается: [id, name, address]
                const tr = document.createElement('tr');
                for (let i = 0; i < 3; i++)
                {
                    const td = document.createElement('td');
                    td.textContent = row[i] || '';
                    tr.appendChild(td);
                }
                tr.appendChild(document.createElement('td'));
                tableBody.appendChild(tr);
            });
        };
        reader.readAsText(file);
    });

    // Функция для загрузки лучших сотрудников по должности
    function loadBestEmployees(positionId)
    {
        fetch('api/employees/best/${positionId}')
            .then(response => response.json())
            .then(data =>
            {
                const container = document.getElementById('bestEmployeesContainer');
                container.innerHTML = ''; // Очистка предыдущих данных

                data.forEach(employee =>
                {
                    container.innerHTML += '' +
                        '<div class="employee-card">' +
                            '<h3>${employee.employee.lastName} ${employee.employee.firstName}</h3>' +
                            '<p>Количество продаж: ${employee.quantitySold}</p>' +
                            '<p>Сумма продаж: ${employee.totalAmount}</p>' +
                        '</div>';
                });
            });
    }

    // загрузка данных о лучшем младшем консультанте
    function loadBestJuniorConsultant()
    {
        fetch('api/positions/best/junior-consultant')
            .then(response => response.json())
            .then(data =>
            {
                const container = document.getElementById('bestJuniorConsultantContainer');
                if (data)
                {
                    container.innerHTML = '' +
                        '<div class="employee-card">' +
                        '<h3>Лучший младший консультант по продаже умных часов</h3>' +
                        '<p>${data.employee.lastName} ${data.employee.firstName}</p>' +
                        '<p>Продано умных часов: ${data.salesCount}</p>' +
                        '</div>';
                } else {
                    container.innerHTML = '<p>Нет данных о продажах умных часов</p>';
                }

            })
            .catch(error =>
            {
                console.error('Ошибка:', error);
                document.getElementById('bestJuniorConsultantContainer').innerHTML = '<p>Ошибка при загрузке данных</p>';
            });
    }

    // загрузка суммы продаж наличными
    function loadCashPayments()
    {
        fetch('api/payments/cash')
            .then(response => response.json())
            .then(amount =>
            {
                document.getElementById('cashPaymentsContainer').innerHTML = '' +
                    '<div class="cash-summary">' +
                    '<p>${amount} руб.</p>'
                    '</div>';
            })
    }