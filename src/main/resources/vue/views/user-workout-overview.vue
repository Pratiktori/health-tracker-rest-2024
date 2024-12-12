<template id="user-workout-overview">
  <div>
    <h3>Workouts list </h3>
    <ul>
      <li v-for="workout in workouts">
        {{workout.id}}: {{workout.description}} for {{workout.duration}} minutes
      </li>
    </ul>
  </div>
</template>

<script>
app.component("user-workout-overview",{
  template: "#user-workout-overview",
  data: () => ({
    workouts: [],
  }),
  created() {
    const userId = this.$javalin.pathParams["user-id"];
    axios.get(`/api/workout/${userId}`)
        .then(res => this.workouts = res.data)
        .catch(() => alert("Error while fetching workouts"));
  }
});
</script>