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