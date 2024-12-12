<template id="user-sleep-overview">
  <div>
    <h3>Sleeping list </h3>
    <ul>
      <li v-for="sleep in sleeping">
        {{sleep.id}}: {{sleep.description}} for {{sleep.duration}} minutes
      </li>
    </ul>
  </div>
</template>

<script>
app.component("user-sleep-overview",{
  template: "#user-sleep-overview",
  data: () => ({
    sleeping: [],
  }),
  created() {
    const userId = this.$javalin.pathParams["user-id"];
    axios.get(`/api/sleep/${userId}`)
        .then(res => this.sleeping = res.data)
        .catch(() => alert("Error while fetching sleeping"));
  }
});
</script>