CREATE SEQUENCE public.review_sequence;

ALTER SEQUENCE public.review_sequence
OWNER TO nss_admin;

CREATE TABLE public.review
(
  id bigint NOT NULL,
  restaurant_id bigint REFERENCES restaurant(id),
  text character varying(255) COLLATE pg_catalog."default",
  CONSTRAINT review_pkey PRIMARY KEY (id)
)
  WITH (
  OIDS = FALSE
       )
  TABLESPACE pg_default;

ALTER TABLE public.review
  OWNER to nss_admin;