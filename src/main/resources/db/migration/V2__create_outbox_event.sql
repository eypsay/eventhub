CREATE TABLE outbox_event (
                              id UUID NOT NULL,
                              event_id UUID NOT NULL,
                              event_type VARCHAR(255) NOT NULL,
                              aggregate_type VARCHAR(255) NOT NULL,
                              aggregate_id UUID NOT NULL,
                              payload TEXT NOT NULL,
                              status VARCHAR(255) NOT NULL,
                              created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                              published_at TIMESTAMP WITH TIME ZONE,
                              retry_count INTEGER NOT NULL,
                              CONSTRAINT pk_outbox_event PRIMARY KEY (id),
                              CONSTRAINT uk_outbox_event_event_id UNIQUE (event_id)
);