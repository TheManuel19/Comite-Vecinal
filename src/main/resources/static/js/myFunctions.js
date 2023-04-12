function cargarColonias() {
    var municipioSeleccionado = document.getElementById("municipios").value;
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("colonias").innerHTML = this.responseText;
        }
    };
    xhr.open("GET", "/colonias-por-municipio?municipio=" + municipioSeleccionado, true);
    xhr.send();
}
