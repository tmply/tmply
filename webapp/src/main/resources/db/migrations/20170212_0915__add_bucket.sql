create table bucket (
  id bigserial primary key,
  name varchar(100) not null unique,
  data text not null,
  expiry_time timestamp without time zone not null
);

create index idx_bucket_expiry_time on bucket ( expiry_time );