package com.example.claudiagalerapract2.ui.listado.events

//F

sealed class ListadoUserEvent {
    //esto hara que el viewModel solo tenga un metodo publico handleEvent.
    // Es to.do lo que hará la pantalla
    //to.do lo de abajo será class cuando tenga parámetros, y object cuando no los tenga
    data object GetUsers : ListadoUserEvent()
    data object DeleteUser : ListadoUserEvent()
    data object AddUser : ListadoUserEvent()
    data object UpdateUser : ListadoUserEvent()

    //hasta aquí to.do será respuesta a un select, a un onclick...

}