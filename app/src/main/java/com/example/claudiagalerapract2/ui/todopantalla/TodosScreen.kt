package com.example.claudiagalerapract2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.claudiagalerapract2.domain.modelo.Todo
import com.example.claudiagalerapract2.ui.todopantalla.TodoViewModel
import com.example.claudiagalerapract2.ui.common.Constantes


@Composable
fun TodoItem(todo: Todo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = todo.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (todo.completed) Constantes.COMPLETED else Constantes.PENDING,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun TodosScreen(
    viewModel: TodoViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val todos = viewModel.todos.collectAsStateWithLifecycle(initialValue = emptyList())

    Column(modifier = modifier.padding(paddingValues)) {
        Text(Constantes.TODOS)
        LazyColumn {
            items(todos.value) { todo ->
                TodoItem(todo = todo)
            }
        }
    }
}