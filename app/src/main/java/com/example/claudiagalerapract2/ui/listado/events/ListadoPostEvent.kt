package com.example.claudiagalerapract2.ui.listado.events

import com.example.claudiagalerapract2.ui.listado.fragments.ListadoPostFragment

sealed class ListadoPostEvent {
    //esto hara que el viewModel solo tenga un metodo publico handleEvent.
    // Es to.do lo que hará la pantalla
    //to.do lo de abajo será class cuando tenga parámetros, y object cuando no los tenga
    data object GetPosts : ListadoPostEvent()

    //hasta aquí to.do será respuesta a un select, a un onclick...

}