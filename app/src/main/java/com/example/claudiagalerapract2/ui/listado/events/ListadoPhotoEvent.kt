package com.example.claudiagalerapract2.ui.listado.events

sealed class ListadoPhotoEvent {
    //esto hara que el viewModel solo tenga un metodo publico handleEvent.
    // Es to.do lo que hará la pantalla
    //to.do lo de abajo será class cuando tenga parámetros, y object cuando no los tenga
    data object GetPhotos : ListadoPhotoEvent()

    //hasta aquí to.do será respuesta a un select, a un onclick...

}