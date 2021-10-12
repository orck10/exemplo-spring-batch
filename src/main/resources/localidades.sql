CREATE TABLE IF NOT EXISTS localidades(id INT PRIMARY KEY AUTO_INCREMENT,
                  cep VARCHAR(20), logradouro VARCHAR(255), complemento VARCHAR(100), bairro VARCHAR(100), localidade VARCHAR(255),
                  uf VARCHAR(2), ibge VARCHAR(20), gia VARCHAR(20), ddd VARCHAR(2), siafi VARCHAR(10));