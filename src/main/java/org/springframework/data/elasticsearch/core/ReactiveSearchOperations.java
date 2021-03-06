/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.elasticsearch.core;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;

/**
 * The reactive operations for the
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search.html">Elasticsearch Document
 * APIs</a>.
 *
 * @author Peter-Josef Meisch
 * @since 4.0
 */
public interface ReactiveSearchOperations {
	/**
	 * Search the index for entities matching the given {@link Query query}. <br />
	 * {@link Pageable#isUnpaged() Unpaged} queries may overrule elasticsearch server defaults for page size by either
	 * delegating to the scroll API or using a max {@link org.elasticsearch.search.builder.SearchSourceBuilder#size(int)
	 * size}.
	 *
	 * @param query must not be {@literal null}.
	 * @param entityType must not be {@literal null}.
	 * @param <T>
	 * @return a {@link Flux} emitting matching entities one by one.
	 */
	default <T> Flux<T> find(Query query, Class<T> entityType) {
		return find(query, entityType, entityType);
	}

	/**
	 * Search the index for entities matching the given {@link Query query}. <br />
	 * {@link Pageable#isUnpaged() Unpaged} queries may overrule elasticsearch server defaults for page size by either *
	 * delegating to the scroll API or using a max {@link org.elasticsearch.search.builder.SearchSourceBuilder#size(int) *
	 * size}.
	 *
	 * @param query must not be {@literal null}.
	 * @param entityType The entity type for mapping the query. Must not be {@literal null}.
	 * @param returnType The mapping target type. Must not be {@literal null}. Th
	 * @param <T>
	 * @return a {@link Flux} emitting matching entities one by one.
	 */
	 <T> Flux<T> find(Query query, Class<?> entityType, Class<T> returnType);

	/**
	 * Search the index for entities matching the given {@link Query query}.
	 *
	 * @param query must not be {@literal null}.
	 * @param entityType must not be {@literal null}.
	 * @param index the target index, must not be {@literal null}
	 * @param <T>
	 * @returnm a {@link Flux} emitting matching entities one by one.
	 */
	default <T> Flux<T> find(Query query, Class<T> entityType, IndexCoordinates index) {
		return find(query, entityType, entityType, index);
	}

	/**
	 * Search the index for entities matching the given {@link Query query}.
	 *
	 * @param query must not be {@literal null}.
	 * @param entityType must not be {@literal null}.
	 * @param resultType the projection result type.
	 * @param index the target index, must not be {@literal null}
	 * @param <T>
	 * @return a {@link Flux} emitting matching entities one by one.
	 */
	<T> Flux<T> find(Query query, Class<?> entityType, Class<T> resultType, IndexCoordinates index);

	/**
	 * Count the number of documents matching the given {@link Query}.
	 *
	 * @param entityType must not be {@literal null}.
	 * @return a {@link Mono} emitting the nr of matching documents.
	 */
	default Mono<Long> count(Class<?> entityType) {
		return count(new StringQuery(QueryBuilders.matchAllQuery().toString()), entityType);
	}

	/**
	 * Count the number of documents matching the given {@link Query}.
	 *
	 * @param query must not be {@literal null}.
	 * @param entityType must not be {@literal null}.
	 * @return a {@link Mono} emitting the nr of matching documents.
	 */
	Mono<Long> count(Query query, Class<?> entityType);

	/**
	 * Count the number of documents matching the given {@link Query}.
	 *
	 * @param query must not be {@literal null}.
	 * @param entityType must not be {@literal null}.
	 * @param index the target index, must not be {@literal null}
	 * @return a {@link Mono} emitting the nr of matching documents.
	 */
	Mono<Long> count(Query query, Class<?> entityType, IndexCoordinates index);

}
