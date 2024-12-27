<template id="workout-overview">
  <app-layout>
    <div class="card bg-light mb-3">
      <div class="card-header">
        <div class="row">
          <div class="col-6">
            Workouts
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
      <form id="addWorkout" @submit.prevent="addWorkout">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-workout-name">Name</span>
          </div>
          <input type="text" class="form-control" v-model="formData.name" name="name" placeholder="Workout name" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-workout-duration">Duration (minutes)</span>
          </div>
          <input type="number" step="0.1" class="form-control" v-model="formData.duration" name="duration" placeholder="How long you worked out" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-workout-caloriesBurned">Calories Burned</span>
          </div>
          <input type="number" class="form-control" v-model="formData.caloriesBurned" name="caloriesBurned" placeholder="Calories Burned" required/>
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="input-workout-performedAt">Performed At</span>
          </div>
          <input type="datetime-local" class="form-control" v-model="formData.performedAt" name="performedAt" required/>
        </div>
        <button type="submit" class="btn btn-primary">Add Workout</button>
      </form>
    </div>

    <div class="list-group list-group-flush">
      <div class="list-group-item d-flex align-items-start"
           v-for="workout in workouts" :key="workout.id">
        <div class="mr-auto p-2">
          <span>
            <a :href="`/workout/${workout.id}`">
              {{ workout.name }} ({{ workout.duration }} minutes, {{ workout.caloriesBurned }} calories),
              Performed at: {{ formatDateTime(workout.performedAt) }}
            </a>
          </span>
        </div>
        <div class="p2">
          <a :href="`/workout/${workout.id}`">
            <button rel="tooltip" title="Update" class="btn btn-info btn-simple btn-link">
              <i class="fa fa-pencil" aria-hidden="true"></i>
            </button>
          </a>
          <button rel="tooltip" title="Delete" class="btn btn-danger btn-simple btn-link"
                  @click.stop.prevent="deleteWorkout(workout.id)">
            <i class="fas fa-trash" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </div>
  </app-layout>
</template>

<script>
app.component("workout-overview", {
  template: "#workout-overview",
  data: () => ({
    workouts: [],
    formData: {
      name: '',
      duration: null,
      caloriesBurned: null,
      performedAt: '',
    },
    hideForm: true,
  }),
  created() {
    this.fetchWorkouts();
  },
  methods: {
    fetchWorkouts: function () {
      axios.get("/api/workout")
          .then(res => this.workouts = res.data)
          .catch(() => alert("Error while fetching workouts"));
    },
    deleteWorkout: function (workoutId) {
      if (confirm('Are you sure you want to delete this workout? This action cannot be undone.', 'Warning')) {
        const url = `/api/workout/${workoutId}`;
        axios.delete(url)
            .then(() => {
              this.workouts = this.workouts.filter(item => item.id !== workoutId);
              alert('Workout deleted successfully');
            })
            .catch(error => {
              console.error("Error deleting workout:", error);
              alert("Failed to delete workout. Please try again.");
            });
      }
    },
    addWorkout: function () {
      const url = `/api/workout`;
      axios.post(url, {
        name: this.formData.name,
        duration: parseFloat(this.formData.duration),
        caloriesBurned: parseInt(this.formData.caloriesBurned),
        performedAt: this.formData.performedAt,
        userId: 1  // You might want to get this dynamically
      })
          .then(response => {
            this.workouts.push(response.data);
            this.hideForm = true;
            this.formData = {
              name: '',
              duration: null,
              caloriesBurned: null,
              performedAt: '',
            };
            alert('Workout added successfully');
          })
          .catch(error => {
            console.error("Error adding workout:", error);
            alert("Failed to add workout. Please try again.");
          });
    },
    formatDateTime: function (dateTime) {
      return new Date(dateTime).toLocaleString();
    }
  }
});
</script>
