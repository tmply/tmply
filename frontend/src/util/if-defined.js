function empty() {
	return [];
}

function ifDefined(value, outputFunc, fallbackFunc) {
	var result;
	if (value)
		 result = (outputFunc||empty)();
	else
		result = (fallbackFunc||empty)();

	return result;
};

export default ifDefined;
