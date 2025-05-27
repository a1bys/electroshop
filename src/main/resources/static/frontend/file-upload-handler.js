let uploadedFileContent = null;

function handleFileSelect(event)
{
    const fileInput = event.target;
    const fileLabel = document.getElementById("selectedFileName");
    const file = fileInput.files[0];

    if (file)
    {
        fileLabel.textContent = `Файл: ${file.name}`;

        const reader = new FileReader();
        reader.onload = function(e)
        {
            uploadedFileContent = e.target.result;
        };
        reader.readAsText(file);
    }
}

function insertDataIntoTable()
{
    if (!uploadedFileContent)
    {
        alert("Сначала выберите файл.");
        return;
    }

    const tbody = document.querySelector("table tbody");
    const lines = uploadedFileContent.trim().split("\n");

    lines.forEach(line =>
    {
        const row = document.createElement("tr");
        const values = line.split(",");
        values.forEach(val =>
        {
            const cell = document.createElement("td");
            cell.textContent = val.trim();
            row.appendChild(cell);
        });
        tbody.appendChild(row);
    });

    uploadedFileContent = null;
    document.getElementById("selectedFileName").textContent = "Файл обработан.";
    document.getElementById("fileInput").value = "";
}
