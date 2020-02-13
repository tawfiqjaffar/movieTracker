const makeResponse = (data, statusCode = 200) => {
    return ({
        code: statusCode,
        data: {...data}
    });
};

module.exports = makeResponse;