document.getElementById("calculateButton").addEventListener("click", () => {
    const equation = document.getElementById("equation").value;
    const y0 = parseFloat(document.getElementById("y0").value);
    const x0 = parseFloat(document.getElementById("x0").value);
    const h = parseFloat(document.getElementById("h").value);
    const n = parseInt(document.getElementById("n").value);
    let f = (x, y) => {};
    try {
        f = eval(`(x, y) => ${equation}`);
    } catch (error) {
        AndroidFunction.showToast("Ошибка");
    }

    let x = [x0];
    let y = [y0];
    let data = [];

    for (let i = 1; i <= n; i++) {
        const myx = x[i - 1] + h;
        const myy = y[i - 1] + h * f(x[i - 1], y[i - 1]);
        data.push([parseFloat(myx).toFixed(3), parseFloat(myy).toFixed(3)]);
        x.push(parseFloat(myx));
        y.push(parseFloat(myy));
    }

    const tbody = document.querySelector("#resultTable tbody");
    tbody.innerHTML = "";

    for (let i = 0; i <= n; i++) {
        const row = tbody.insertRow();
        const cellX = row.insertCell(0);
        const cellY = row.insertCell(1);
        cellX.textContent = x[i].toFixed(3);
        cellY.textContent = y[i].toFixed(3);
    }

    chart = anychart.line();
    chart.line(data);
    chart.container("canvas");
    chart.draw();
});
