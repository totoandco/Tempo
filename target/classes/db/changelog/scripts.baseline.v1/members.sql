CREATE TABLE public.members
(
    id uuid NOT NULL,
    role_id uuid NOT NULL,
    user_id uuid NOT NULL,
    team_id uuid,
     CONSTRAINT member_id_pkey PRIMARY KEY (id),
    CONSTRAINT member_role_fkey FOREIGN KEY (role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);
ALTER table members
    ADD UNIQUE (role_id, user_id,team_id);
