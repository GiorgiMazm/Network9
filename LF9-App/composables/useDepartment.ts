export default () => {
  async function getDataByDepartment(department: string) {
    const { error, data } = await useFetch(
      `http://localhost:8080/department/${department.toLowerCase()}`
    );
    if (error.value) {
      throw createError({
        statusCode: 404,
        statusMessage:
          "Something went wrong with fetching data, try again later",
      });
    }
    return [data.value];
  }

  async function getDepartments() {
    const { error, data } = await useFetch(`http://localhost:8080/departments`);
    if (error.value) {
      throw createError({
        statusCode: 404,
        statusMessage:
          "Something went wrong with fetching data, try again later",
      });
    }
    return data.value as string[];
  }

  async function createDepartment(department: object) {
    const { error } = await useFetch(
      `http://localhost:8080/create/department`,
      {
        method: "post",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(department),
      }
    );
    if (error.value) {
      throw createError({
        statusCode: 404,
        statusMessage:
          "Something went wrong with fetching data, try again later",
      });
    }
  }

  return { getDataByDepartment, getDepartments, createDepartment };
};
