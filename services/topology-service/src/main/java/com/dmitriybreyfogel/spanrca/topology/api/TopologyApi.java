package com.dmitriybreyfogel.spanrca.topology.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/topology")
public interface TopologyApi {

    /**
     * Импортирование топологии, полученной суммированием топологий всех директорий сервисов системы.
     * При импортировании данные провалидируются, обработаются и свяжутся в единый граф сервисов системы
     */
    @PostMapping("/scan")
    ResponseEntity<TopologyScanDto> importTopologyScan(
            @RequestBody
            TopologyScanRequest request
    );

    /**
     * Получение построенного графа топологии системы сервисов
     */
    @GetMapping()
    ResponseEntity<GraphDto> getGraphTopology();

    /**
     * Получение данных имеющейся топологии
     */
    @GetMapping("/stats")
    ResponseEntity<TopologyStatsDto> getTopologyStats();

    /**
     * Получение конкретного узла топологии
     */
    @GetMapping("/nodes/{nodeId}")
    ResponseEntity<TopologyNodeDto> getTopologyNode(
            @PathVariable
            Long nodeId
    );

    /**
     * Получение прямых зависимостей узла.
     * К кому обращается текущий сервис в системе
     */
    @GetMapping("/nodes/{nodeId}/dependencies")
    ResponseEntity<TopologyNodeDependenciesDto> getTopologyNodeDependencies(
            @PathVariable
            Long nodeId
    );

    /**
     * Получение зависимых узлов от текущего
     * Кто обращается к текущему сервису в системе
     */
    @GetMapping("/nodes/{nodeId}/dependents")
    ResponseEntity<TopologyNodeDependetsDto> getTopologyNodeDependents(
            @PathVariable
            Long nodeId
    );

    /**
     * Получение подграфа по заданным параметрам для конкретного узла
     */
    @GetMapping("/nodes/{nodeId}/subgraph")
    ResponseEntity<GraphInformationDto> getSubgraph(
            @PathVariable Long nodeId,
            @RequestParam(defaultValue = "1") @Min(1) @Max(10) int depth,
            @RequestParam(defaultValue = "BOTH") TraversalDirection direction
    );
}
