# Overview

This is the inception of tryiong to create a spring-security module
that supports macaroons.

It's very early to tell if this will work, though if you have any input, contact
me and I can add you as a contributor.

More coming soon.

## Caveats (Yes that is a joke)
Since we are trying to retrofit a decentralized auth system to a system that assumes
a centralised authentication server there are some caveats (pun intended) we need to follow:

<ul>
    <li>The macaroon id is what we will map to the principal</li>
    <li>Credentials will be populated with the secret of the macaroon</li>
</ul>