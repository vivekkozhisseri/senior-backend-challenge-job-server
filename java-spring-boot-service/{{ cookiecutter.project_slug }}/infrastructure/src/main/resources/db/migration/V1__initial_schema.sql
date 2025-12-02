/* This is just to let the code run with flyway. Replace this with your schema. */

CREATE TABLE resources (
  id CHAR(36) NOT NULL,
  property_one VARCHAR(10) NOT NULL,
  property_two VARCHAR(10) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT now(),
  updated_at TIMESTAMP NOT NULL DEFAULT now(),
  PRIMARY KEY ( id )
);

CREATE INDEX resorces_property_one_idx ON resources(property_one);