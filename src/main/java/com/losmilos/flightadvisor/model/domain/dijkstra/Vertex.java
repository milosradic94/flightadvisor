package com.losmilos.flightadvisor.model.domain.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Vertex {

    final private Long id;

    final private String cityName;

    @Override
    public int hashCode() {
        return 31 * 7 + ((id == null) ? 0 : id.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Vertex other = (Vertex) obj;

        if (id == null && other.id != null)
            return false;

        if (!id.equals(other.id))
            return false;

        return true;
    }
}
