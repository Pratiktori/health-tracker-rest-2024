<template id="user-nutrition-overview">
  <div>
    <h3>Diet list </h3>
    <ul>
      <li v-for="nutrition in diet">
        {{nutrition.id}}: {{nutrition.description}} for {{nutrition.duration}} minutes
      </li>
    </ul>
  </div>
</template>

<script>
app.component("user-nutrition-overview",{
  template: "#user-nutrition-overview",
  data: () => ({
    diet: [],
  }),
  created() {
    const userId = this.$javalin.pathParams["user-id"];
    axios.get(`/api/nutrition/${userId}`)
        .then(res => this.diet = res.data)
        .catch(() => alert("Error while fetching diet"));
  }
});
</script>