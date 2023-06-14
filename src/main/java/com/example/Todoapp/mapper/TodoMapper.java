package com.example.Todoapp.mapper;
import com.example.Todoapp.dto.TodoDto;
import com.example.Todoapp.entity.Todo;
import com.example.Todoapp.request.TodoRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
               nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
                nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public  interface TodoMapper {
    List<TodoDto> mapDTO(List<Todo> todo);


    TodoDto mapDTO(Todo todo);


    Todo map(TodoRequest todoRequest);

}