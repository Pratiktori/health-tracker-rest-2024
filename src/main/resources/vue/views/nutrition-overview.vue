<template id="nutrition-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Nutrition List
          </div>
          <div class="col" align="right">
            <button rel="tooltip" title="Add"
                    class="btn btn-info btn-simple btn-link"
                    @click="hideForm = !hideForm">
              <i class="fa fa-plus" aria-hidden="true"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="card-body" :class="{ 'd-none': hideForm }">
      <form id="addNutrition" @submit.prevent="addNutrition">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-nutrition-name">Food Name</span>
          </div>
          <input type="text" class="form-control" v-model="formData.foodName" name="foodName" placeholder="What would you like to have" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-nutrition-calories">Calories</span>
          </div>
          <input type="number" class="form-control" v-model="formData.calories" name="calories" placeholder="Calories" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-nutrition-consumedAt">Consumed At</span>
          </div>
          <input type="datetime-local" class="form-control" v-model="formData.consumedAt" name="consumedAt" required/>
        </div>
        <button type="submit" class="btn btn-primary">Add Nutrition</button>
      </form>
    </div>
    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="nutrition in diet" :key="nutrition.id">
        <div class="mr-auto p-2">
          <span>
            <a :href="`/nutrition/${nutrition.id}`">
              {{ nutrition.foodName }} ({{ nutrition.calories }} calories),
              Consumed at: {{ formatDateTime(nutrition.consumedAt) }}
            </a>
          </span>
        </div>
        <div class="p2">
          <a :href="`/nutrition/${nutrition.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
          </a>
          <button rel="tooltip" title="Delete" class="btn btn-danger btn-simple btn-link"
                  @click.stop.prevent="deleteNutrition(nutrition.id)">
            <i class="fas fa-trash" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("nutrition-overview", {
  template: "#nutrition-overview",
  data: () => ({
    diet: [],
    formData: {
      foodName: '',
      calories: null,
      consumedAt: '',
    },
    hideForm: true,
  }),
  created() {
    this.fetchDiet();
  },
  methods: {
    fetchDiet: function () {
      axios.get("/api/nutrition")
          .then(res => this.diet = res.data)
          .catch(() => alert("Error while fetching nutrition data"));
    },
    deleteNutrition: function (nutritionId) {
      if (confirm('Are you sure you want to delete this nutrition entry? This action cannot be undone.', 'Warning')) {
        const url = `/api/nutrition/${nutritionId}`;
        axios.delete(url)
            .then(() => {
              this.diet = this.diet.filter(item => item.id !== nutritionId);
              alert('Nutrition entry deleted successfully');
            })
            .catch(error => {
              console.error("Error deleting nutrition entry:", error);
              alert("Failed to delete nutrition entry. Please try again.");
            });
      }
    },
    addNutrition: function () {
      const url = `/api/nutrition`;
      axios.post(url, {
        foodName: this.formData.foodName,
        calories: parseInt(this.formData.calories),
        consumedAt: this.formData.consumedAt,
        userId: 1  // You might want to get this dynamically
      })
          .then(response => {
            this.diet.push(response.data);
            this.hideForm = true;
            this.formData = {
              foodName: '',
              calories: null,
              consumedAt: '',
            };
            alert('Nutrition entry added successfully');
          })
          .catch(error => {
            console.error("Error adding nutrition entry:", error);
            alert("Failed to add nutrition entry. Please try again.");
          });
    },
    formatDateTime: function (dateTime) {
      return new Date(dateTime).toLocaleString();
    }
  }
});
</script>

