let uploadedFile = null;
let uploadedFileName = "";

function handleFileSelect(event)
{
    uploadedFile = event.target.files[0];
    uploadedFileName = uploadedFile.name;
    document.getElementById("selectedFileName").textContent = `Выбран: ${uploadedFileName}`;
}

function insertDataIntoTable()
{
    if (!uploadedFile)
    {
        alert("Сначала выберите файл.");
        return;
    }

    const ext = uploadedFile.name.split('.').pop().toLowerCase();

    if (ext === "csv")
    {
        handleCSV(uploadedFile);
    } else if (ext === "vcf")
    {
        handleVCF(uploadedFile);
    } else if (ext === "zip")
    {
        handleZIP(uploadedFile);
    } else
    {
        alert("Неподдерживаемый формат. Допустимы: .csv, .vcf, .zip");
    }
}

// CSV
function handleCSV(file)
{
    const reader = new FileReader();
    reader.onload = function (e)
    {
        const content = e.target.result;
        const lines = content.trim().split("\n");

        const tbody = document.querySelector("table tbody");

        lines.forEach(line =>
        {
            const row = document.createElement("tr");
            const cells = line.split(",");
            cells.forEach(value =>
            {
                const td = document.createElement("td");
                td.textContent = value.trim();
                row.appendChild(td);
            });
            tbody.appendChild(row);
        });

        cleanup("Данные из CSV добавлены");
    };
    reader.readAsText(file);
}

// VCF
function handleVCF(file)
{
    const reader = new FileReader();
    reader.onload = function (e)
    {
        const content = e.target.result;
        const lines = content.split("\n");

        const tbody = document.querySelector("tbody");
        let row = document.createElement("tr");
        let fullName = "", email = "", phone = "";

        lines.forEach(line =>
        {
            if (line.startsWith("FN:")) fullName = line.replace("FN:", "").trim();
            if (line.startsWith("EMAIL")) email = line.split(":")[1].trim();
            if (line.startsWith("TEL")) phone = line.split(":")[1].trim();
            if (line.startsWith("END:VCARD"))
            {
                row = document.createElement("tr");

                [fullName, email, phone].forEach(text =>
                {
                    const td = document.createElement("td");
                    td.textContent = text;
                    row.appendChild(td);
                });

                tbody.appendChild(row);
                fullName = email = phone = "";
            }
        });

        cleanup("Контакты из VCF добавлены");
    };
    reader.readAsText(file);
}

// ZIP
function handleZIP(file)
{
    const reader = new FileReader();
    reader.onload = function (e)
    {
        JSZip.loadAsync(e.target.result).then(zip =>
        {
            const tbody = document.querySelector("tbody");

            zip.forEach((relativePath, zipEntry) =>
            {
                if (zipEntry.name.endsWith(".csv"))
                {
                    zipEntry.async("string").then(text =>
                    {
                        const lines = text.trim().split("\n");

                        lines.forEach(line =>
                        {
                            const row = document.createElement("tr");
                            const cells = line.split(",");
                            cells.forEach(value =>
                            {
                                const td = document.createElement("td");
                                td.textContent = value.trim();
                                row.appendChild(td);
                            });
                            tbody.appendChild(row);
                        });
                    });
                }

                else if (zipEntry.name.endsWith(".vcf"))
                {
                    zipEntry.async("string").then(text =>
                    {
                        const lines = text.split("\n");

                        let row = document.createElement("tr");
                        let fullName = "", email = "", phone = "";

                        lines.forEach(line =>
                        {
                            if (line.startsWith("FN:")) fullName = line.replace("FN:", "").trim();
                            if (line.startsWith("EMAIL")) email = line.split(":")[1].trim();
                            if (line.startsWith("TEL")) phone = line.split(":")[1].trim();
                            if (line.startsWith("END:VCARD"))
                            {
                                row = document.createElement("tr");

                                [fullName, email, phone].forEach(text =>
                                {
                                    const td = document.createElement("td");
                                    td.textContent = text;
                                    row.appendChild(td);
                                });

                                tbody.appendChild(row);
                                fullName = email = phone = "";
                            }
                        });
                    });
                }
            });

            cleanup("Файлы CSV/VCF из ZIP добавлены");
        });
    };
    reader.readAsArrayBuffer(file);
}

function cleanup(message)
{
    document.getElementById("selectedFileName").textContent = message;
    document.getElementById("fileInput").value = "";
    uploadedFile = null;
}