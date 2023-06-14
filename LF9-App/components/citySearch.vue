<script lang="ts" setup>
import useCity from "~/composables/useCity";
import useDepartment from "~/composables/useDepartment";

const props = defineProps({
  type: {
    type: String,
    required: true,
  },
});

const inputValue = ref("");
let selectArray: string | string[];
const cities = useCity();
const departments = useDepartment();
if (props.type === "city") {
  selectArray = await cities.getCities();
  selectArray = selectArray
    .slice(1, -1)
    .split(",")
    .map((item) => item.trim());
} else if (props.type === "department") {
  selectArray = departments.department;
}

const isShowResult = ref(false);
const results = reactive([]);

async function handleSearch() {
  if (props.type === "city") {
    // @ts-ignore
    results.values = await cities.getDataByCity(inputValue.value);
  } else if (props.type === "department") {
    // @ts-ignore
    results.values = await departments.getDataByDepartment(inputValue.value);
  }
  isShowResult.value = true;
}
</script>

<template>
  <div>
    <div class="mt-20">
      <label class="mr-2">Choose a {{ props.type }}:</label>
      <select :name="props.type" v-model="inputValue">
        <option v-for="item in selectArray" :value="item.name ?? item">
          {{
            item.name?.toString().toUpperCase() ?? item.toString().toUpperCase()
          }}
        </option>
      </select>

      <button
        class="rounded-xl bg-black py-2 px-4 hover:bg-red-600 mx-2"
        @click="handleSearch"
        :name="props.type"
      >
        Submit
      </button>

      <div v-if="isShowResult" id="result">
        Your results:
        <p v-for="result in results.values">{{ result }}</p>
      </div>
      <br />
      <button
        class="rounded-xl bg-gray-800 py-2 px-4 hover:bg-amber-800"
        @click="isShowResult = false"
        :name="'clear-' + props.type"
      >
        clear
      </button>
    </div>
  </div>
</template>
