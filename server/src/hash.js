let bcrypt = require('bcrypt');

const passwordToHash = async (password) => {
    let hash = await bcrypt.hash(password, 10);
    return (hash);
};

const hashToPassword = async (pass_a, pass_b) => {
    let resp = await bcrypt.compare(pass_a, pass_b);
    return resp;
};

const hash = {
    passwordToHash: passwordToHash,
    hashToPassword: hashToPassword
};

module.exports = hash;