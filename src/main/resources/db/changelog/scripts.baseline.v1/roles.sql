CREATE TABLE public.roles
(
    id uuid NOT NULL,
    name text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
)