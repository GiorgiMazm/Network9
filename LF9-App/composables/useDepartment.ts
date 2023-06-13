import department from "../data/department.json";
export default () => {
  async function getDataByDepartment(department: string) {
    // const { error, data } = await useFetch(
    //   `http://localhost:8080/department/${department}`
    // );
    // if (error.value) {
    //   throw createError({
    //     statusCode: 404,
    //     statusMessage:
    //       "Something went wrong with fetching data, try again later",
    //   });
    // }
    //  return data.value;

    return [department, "hoo"];
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
    return data.value;
  }

  async function createDepartment(department: object) {
    // const { error, data } = await useFetch(
    //   `http://localhost:8080/new/department`,
    //   {
    //     method: "post",
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //     body: JSON.stringify(department),
    //   }
    // );
    // if (error.value) {
    //   throw createError({
    //     statusCode: 404,
    //     statusMessage:
    //       "Something went wrong with fetching data, try again later",
    //   });
    // }

    console.log(department);
  }

  return { department, getDataByDepartment, getDepartments, createDepartment };
};
