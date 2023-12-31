export default () => {
  async function getDataByCity(city: string) {
    const { error, data } = await useFetch(
      `http://localhost:8080/city/${city}`
    );
    if (error.value) {
      throw createError({
        statusCode: 404,
        statusMessage:
          "Something went wrong with fetching data, try again later",
      });
    }
    return data.value as string[];
  }

  async function getCities() {
    const { error, data } = await useFetch(`http://localhost:8080/cities`);
    if (error.value) {
      throw createError({
        statusCode: 404,
        statusMessage:
          "Something went wrong with fetching data, try again later",
      });
    }
    return data.value as string[];
  }
  return { getDataByCity, getCities };
};
