<script lang="ts" setup>
const newDepartment = ref(false);
const city = ref("");
const department = ref("");
function toggleNewForm() {
  newDepartment.value = !newDepartment.value;
  clearInput();
}

function clearInput() {
  city.value = "";
  department.value = "";
}

const { createDepartment } = useDepartment();
</script>

<template>
  <div class="bg-gray-700 h-screen text-gray-400">
    <section>
      <div class="container mx-auto">
        <h1 class="text-5xl font-bold text-center pt-14">
          Welcome to network Admin page!
        </h1>
        <CitySearch type="city" />
        <CitySearch type="department" />

        <button
          class="rounded-xl bg-white text-black mt-14 py-4 px-6 hover:bg-red-600 mx-2"
          v-if="!newDepartment"
          @click="toggleNewForm"
        >
          Create new department
        </button>
        <div v-if="newDepartment" class="p-6 border-2 mt-20" id="newDepartment">
          <form>
            <div class="mb-4">
              <input name="city" class="mr-3" type="text" v-model="city" />
              <label>City</label>
            </div>
            <div>
              <input
                name="department"
                class="mr-3"
                type="text"
                v-model="department"
              />
              <label>Department</label>
            </div>
          </form>
          <div class="mt-5">
            <button
              class="rounded-xl bg-black py-4 px-6 hover:bg-red-600 mx-2"
              v-if="newDepartment"
              @click="
                createDepartment({ name: department, location: city });
                toggleNewForm();
              "
            >
              Create
            </button>
            <button
              v-if="newDepartment"
              @click="toggleNewForm"
              class="rounded-xl bg-black py-4 px-6 hover:bg-red-600"
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
